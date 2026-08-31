package com.osb.youtube.controller;

import com.osb.youtube.dto.response.VideoResponse;
import com.osb.youtube.service.interfaces.TrendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trending")
@RequiredArgsConstructor
public class TrendingController {

    private final TrendingService trendingService;

    @GetMapping
    public ResponseEntity<List<VideoResponse>> getTrendingVideos() {
        return ResponseEntity.ok(
                trendingService.getTrendingVideos()
        );
    }
}