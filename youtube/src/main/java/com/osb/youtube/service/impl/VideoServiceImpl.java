package com.osb.youtube.service.impl;
import com.osb.youtube.dto.request.VideoUploadRequest;
import com.osb.youtube.dto.response.VideoResponse;
import com.osb.youtube.entity.*;
import com.osb.youtube.enums.LikeStatus;
import com.osb.youtube.exception.*;
import com.osb.youtube.mapper.VideoMapper;
import com.osb.youtube.repository.*;
import com.osb.youtube.service.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    private final ChannelRepository channelRepository;
    private final CategoryRepository categoryRepository;
    private final S3Service s3Service;
    private final UserRepository userRepository;
    private final VideolikeRepository videoLikeRepository;
    private final VideoLikeService videoLikeService;
    private final VideoViewRepository videoViewRepository;
    private final VideoMapper videoMapper;
    private final ChannelMembershipService channelMembershipService;
    private final WatchHistoryService watchHistoryService;

    @Override
    public void uploadVideo(VideoUploadRequest request) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Channel channel =
                channelRepository.findByUserId(user.getId())
                        .orElseThrow(() ->
                                new ChannelNotFoundException(
                                        "Channel not found for user: " + username
                                )
                        );

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Category not found with id: " + request.getCategoryId()
                        )
                );

        String videoUrl = s3Service.uploadFile(
                request.getVideoFile(),
                "videos"
        );

        String thumbnailUrl = s3Service.uploadFile(
                request.getThumbnailFile(),
                "thumbnails"
        );

        Video video = new Video();
        video.setVideoTitle(request.getVideoTitle());
        video.setVideoDescription(request.getVideoDescription());
        video.setVideoUrl(videoUrl);
        video.setThumbnailUrl(thumbnailUrl);
        video.setChannel(channel);
        video.setCategory(category);
        video.setViews(0);
        video.setDuration(0);

        videoRepository.save(video);
    }
    @Override
    public List<VideoResponse> getAllVideos() {

        List<Video> videos = videoRepository.findAll();

        return videos.stream().map(video -> {

            VideoResponse response = videoMapper.toResponse(video);

            if (Boolean.TRUE.equals(video.getMembersOnly())) {

                String channelId = video.getChannel().getId();

                boolean member =
                        channelMembershipService.isMember(channelId);

                if (!member) {
                    response.setVideoUrl(null);
                }
            }

            return response;

        }).toList();
    }
    @Override
    public VideoResponse getVideoById(String videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));
        VideoResponse response = new VideoResponse();
        response.setId(video.getId());
        response.setVideoTitle(video.getVideoTitle());
        response.setVideoDescription(video.getVideoDescription());
        response.setVideoUrl(video.getVideoUrl());
        response.setThumbnailUrl(video.getThumbnailUrl());
        response.setViews(video.getViews());
        response.setDuration(video.getDuration());
        response.setChannelId(video.getChannel().getId());
        response.setChannelName(video.getChannel().getChannelName());
        response.setCategoryName(video.getCategory().getCategoryName());
        response.setLikeCount(
                videoLikeService.getLikeCount(video.getId())
        );
        response.setDislikeCount(
                videoLikeService.getDislikeCount(video.getId())
        );

        return response;
    }
    @Override
    public VideoResponse watchVideo(String videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));
        if (Boolean.TRUE.equals(video.getMembersOnly())) {
            String channelId = video.getChannel().getId();
            if (!channelMembershipService.isMember(channelId)) {
                throw new UnauthorizedException(
                        "You must be a member of this channel to watch this video"
                );
            }
        }
        video.setViews(video.getViews() + 1);
        videoRepository.save(video);
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (authentication != null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {
            String username = authentication.getName();
            user = userRepository.findByUserName(username)
                    .orElse(null);
        }
        VideoView videoView = new VideoView();
        videoView.setVideo(video);
        videoView.setUser(user);
        videoView.setViewedAt(java.time.LocalDateTime.now());
        videoViewRepository.save(videoView);
        watchHistoryService.addToHistory(videoId);
        return videoMapper.toResponse(video);
    }
    @Override
    public void deleteVideo(String videoId) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() ->
                        new VideoNotFoundException("Video not found with id: " + videoId));
        if (!video.getChannel().getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException(
                    "You can delete only your own videos"
            );
        }
        s3Service.deleteFile(video.getVideoUrl());
        s3Service.deleteFile(video.getThumbnailUrl());
        videoRepository.delete(video);
    }
    @Override
    public void updateVideo(String videoId, VideoUploadRequest request) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));
        if (!video.getChannel().getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("You can update only your own videos");
        }
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Category not found with id: " + request.getCategoryId()
                        )
                );
        video.setVideoTitle(request.getVideoTitle());
        video.setVideoDescription(request.getVideoDescription());
        video.setCategory(category);
        if (request.getVideoFile() != null && !request.getVideoFile().isEmpty()) {
            s3Service.deleteFile(video.getVideoUrl());
            String videoUrl = s3Service.uploadFile(
                    request.getVideoFile(),
                    "videos"
            );
            video.setVideoUrl(videoUrl);
        }

        if (request.getThumbnailFile() != null && !request.getThumbnailFile().isEmpty()) {
            s3Service.deleteFile(video.getThumbnailUrl());
            String thumbnailUrl = s3Service.uploadFile(
                    request.getThumbnailFile(),
                    "thumbnails"
            );
            video.setThumbnailUrl(thumbnailUrl);
        }
        video.setMembersOnly(
                request.getMembersOnly() != null
                        && request.getMembersOnly()
        );
        videoRepository.save(video);
    }
    @Override
    public void reactToVideo(String videoId, LikeStatus status) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));
        VideoLike videoLike = videoLikeRepository
                .findByUserIdAndVideoId(user.getId(), videoId)
                .orElse(null);
        if (videoLike == null) {
            videoLike = new VideoLike();
            videoLike.setUser(user);
            videoLike.setVideo(video);
        }
        videoLike.setStatus(status);
        videoLikeRepository.save(videoLike);
    }
    @Override
    public List<VideoResponse> getVideosByCategory(String categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        List<Video> videos = videoRepository.findByCategoryId(categoryId);
        return videos.stream()
                .map(videoMapper::toResponse)
                .toList();
    }
    @Override
    public List<VideoResponse> searchVideos(String query) {
        List<Video> videos =
                videoRepository
                        .findByVideoTitleContainingIgnoreCaseOrVideoDescriptionContainingIgnoreCase(
                                query,
                                query
                        );
        return videos.stream()
                .map(videoMapper::toResponse)
                .toList();
    }
    @Override
    public List<VideoResponse> getVideosByChannel(String channelId) {
        channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFoundException("Channel not found"));
        return videoRepository.findByChannelId(channelId)
                .stream()
                .map(videoMapper::toResponse)
                .toList();
    }
    @Override
    public List<VideoResponse> getTrendingVideos() {
        return videoRepository.findTop20ByOrderByViewsDesc()
                .stream()
                .map(videoMapper::toResponse)
                .toList();
    }
}
