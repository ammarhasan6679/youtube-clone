package com.osb.youtube.controller;

import com.osb.youtube.dto.response.WatchLaterResponse;
import com.osb.youtube.service.interfaces.WatchLaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watch-later")
@RequiredArgsConstructor
public class WatchLaterController {

    private final WatchLaterService watchLaterService;

    @PostMapping("/{videoId}")
    public ResponseEntity<String> addToWatchLater(
            @PathVariable String videoId) {
        watchLaterService.addToWatchLater(videoId);
        return ResponseEntity.ok("Video added to Watch Later");
    }

    @GetMapping
    public ResponseEntity<List<WatchLaterResponse>> getWatchLater() {
        return ResponseEntity.ok(
                watchLaterService.getWatchLater()
        );
    }

    @DeleteMapping("/{videoId}")
    public ResponseEntity<String> removeFromWatchLater(
            @PathVariable String videoId) {
        watchLaterService.removeFromWatchLater(videoId);
        return ResponseEntity.ok(
                "Video removed from Watch Later"
        );
    }
}