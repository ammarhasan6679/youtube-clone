package com.osb.youtube.service.impl;

import com.osb.youtube.entity.User;
import com.osb.youtube.entity.Video;
import com.osb.youtube.entity.VideoLike;
import com.osb.youtube.enums.LikeStatus;
import com.osb.youtube.repository.UserRepository;
import com.osb.youtube.repository.VideoRepository;
import com.osb.youtube.repository.VideolikeRepository;
import com.osb.youtube.service.interfaces.VideoLikeService;
import com.osb.youtube.service.interfaces.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoLikeServiceImpl implements VideoLikeService {
    private final VideolikeRepository videoLikeRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    @Override
    public void reactToVideo(String videoId, LikeStatus status) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));
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
    public long getLikeCount(String videoId) {
        return videoLikeRepository.countByVideoIdAndStatus(
                videoId,
                LikeStatus.LIKE
        );
    }

    @Override
    public long getDislikeCount(String videoId) {
        return videoLikeRepository.countByVideoIdAndStatus(
                videoId,
                LikeStatus.DISLIKE
        );
    }
    @Override
    public void removeReaction(String videoId) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        VideoLike videoLike = videoLikeRepository
                .findByUserIdAndVideoId(user.getId(), videoId)
                .orElseThrow(() -> new RuntimeException("Reaction not found"));
        videoLikeRepository.delete(videoLike);
    }
}
