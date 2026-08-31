package com.osb.youtube.service.interfaces;

import com.osb.youtube.dto.request.VideoUploadRequest;
import com.osb.youtube.dto.response.VideoResponse;
import com.osb.youtube.entity.Video;
import com.osb.youtube.enums.LikeStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    void uploadVideo(VideoUploadRequest request);
    List<VideoResponse> getAllVideos();
    VideoResponse getVideoById(String videoId);
    VideoResponse watchVideo(String videoId);
    void deleteVideo(String videoId);
    void updateVideo(String videoId, VideoUploadRequest request);
    void reactToVideo(String videoId, LikeStatus status);
    List<VideoResponse> searchVideos(String query);
    List<VideoResponse> getVideosByCategory(String categoryId);
    List<VideoResponse> getVideosByChannel(String channelId);
    List<VideoResponse> getTrendingVideos();
}
