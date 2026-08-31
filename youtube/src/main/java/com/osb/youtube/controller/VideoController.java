package com.osb.youtube.controller;

import com.osb.youtube.dto.request.VideoUploadRequest;
import com.osb.youtube.dto.response.VideoResponse;
import com.osb.youtube.enums.LikeStatus;
import com.osb.youtube.service.interfaces.VideoService;
import com.osb.youtube.service.interfaces.WatchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    private final WatchHistoryService watchHistoryService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadVideo(
            @RequestParam("videoTitle") String videoTitle,
            @RequestParam("videoDescription") String videoDescription,
            @RequestParam("categoryId") String categoryId,
            @RequestParam("videoFile") MultipartFile videoFile,
            @RequestParam("thumbnailFile") MultipartFile thumbnailFile
    ) {
        VideoUploadRequest request = new VideoUploadRequest();
        request.setVideoTitle(videoTitle);
        request.setVideoDescription(videoDescription);
        request.setCategoryId(categoryId);
        request.setVideoFile(videoFile);
        request.setThumbnailFile(thumbnailFile);
        videoService.uploadVideo(request);
        return ResponseEntity.ok("Video uploaded successfully");
    }
    @GetMapping
    public ResponseEntity<List<VideoResponse>> getAllVideos() {
        return ResponseEntity.ok(videoService.getAllVideos());
    }
    @GetMapping("/{videoId}")
    public ResponseEntity<VideoResponse> getVideoById(
            @PathVariable String videoId) {
        return ResponseEntity.ok(videoService.getVideoById(videoId));
    }
    @GetMapping("/watch/{videoId}")
    public ResponseEntity<VideoResponse> watchVideo(
            @PathVariable String videoId) {
        return ResponseEntity.ok(
                videoService.watchVideo(videoId)
        );
    }
    @DeleteMapping("/{videoId}")
    public ResponseEntity<String> deleteVideo(
            @PathVariable String videoId) {
        videoService.deleteVideo(videoId);
        return ResponseEntity.ok("Video deleted successfully");
    }
    @PutMapping("/{videoId}")
    public ResponseEntity<String> updateVideo(
            @PathVariable String videoId,
            @ModelAttribute VideoUploadRequest request) {
        videoService.updateVideo(videoId, request);
        return ResponseEntity.ok("Video updated successfully");
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<VideoResponse>> getVideosByCategory(
            @PathVariable String categoryId) {
        return ResponseEntity.ok(
                videoService.getVideosByCategory(categoryId)
        );
    }
    @GetMapping("/search")
    public ResponseEntity<List<VideoResponse>> searchVideos(
            @RequestParam String query) {
        return ResponseEntity.ok(
                videoService.searchVideos(query)
        );
    }
    @GetMapping("/channel/{channelId}")
    public ResponseEntity<List<VideoResponse>> getVideosByChannel(
            @PathVariable String channelId) {
        return ResponseEntity.ok(
                videoService.getVideosByChannel(channelId)
        );

    }
    @GetMapping("/trending")
    public ResponseEntity<List<VideoResponse>> getTrendingVideos() {
        return ResponseEntity.ok(
                videoService.getTrendingVideos()
        );
    }
}