package com.osb.youtube.controller;

import com.osb.youtube.dto.response.WatchHistoryResponse;
import com.osb.youtube.service.interfaces.WatchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watch-history")
@RequiredArgsConstructor
public class WatchHistoryController {

    private final WatchHistoryService watchHistoryService;

    @PostMapping("/{videoId}")
    public ResponseEntity<String> addToHistory(
            @PathVariable String videoId) {

        watchHistoryService.addToHistory(videoId);

        return ResponseEntity.ok("Added to watch history");
    }

    @GetMapping
    public ResponseEntity<List<WatchHistoryResponse>> getHistory() {
        return ResponseEntity.ok(
                watchHistoryService.getHistory()
        );
    }

    @DeleteMapping("/{videoId}")
    public ResponseEntity<String> removeFromHistory(
            @PathVariable String videoId) {
        watchHistoryService.removeFromHistory(videoId);
        return ResponseEntity.ok("Removed from watch history");
    }

    @DeleteMapping
    public ResponseEntity<String> clearHistory() {
        watchHistoryService.clearHistory();
        return ResponseEntity.ok("Watch history cleared");
    }
}