package com.osb.youtube.controller;
import com.osb.youtube.enums.LikeStatus;
import com.osb.youtube.service.interfaces.VideoLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/video-likes")
@RequiredArgsConstructor
public class VideoLikeController {

    private final VideoLikeService videoLikeService;

    @PostMapping("/{videoId}")
    public ResponseEntity<String> reactToVideo(
            @PathVariable String videoId,
            @RequestParam LikeStatus status) {
        videoLikeService.reactToVideo(videoId, status);
        return ResponseEntity.ok("Reaction saved successfully");
    }
    @DeleteMapping("/{videoId}")
    public ResponseEntity<String> removeReaction(
            @PathVariable String videoId) {
        videoLikeService.removeReaction(videoId);
        return ResponseEntity.ok("Reaction removed successfully");
    }
    @GetMapping("/{videoId}/likes")
    public ResponseEntity<Long> getLikeCount(
            @PathVariable String videoId) {

        return ResponseEntity.ok(
                videoLikeService.getLikeCount(videoId)
        );
    }

    @GetMapping("/{videoId}/dislikes")
    public ResponseEntity<Long> getDislikeCount(
            @PathVariable String videoId) {

        return ResponseEntity.ok(
                videoLikeService.getDislikeCount(videoId)
        );
    }
}
