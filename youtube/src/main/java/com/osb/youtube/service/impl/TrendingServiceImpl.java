package com.osb.youtube.service.impl;

import com.osb.youtube.dto.response.VideoResponse;
import com.osb.youtube.entity.Video;
import com.osb.youtube.enums.LikeStatus;
import com.osb.youtube.mapper.VideoMapper;
import com.osb.youtube.repository.CommentRepository;
import com.osb.youtube.repository.VideoRepository;
import com.osb.youtube.repository.VideoViewRepository;
import com.osb.youtube.repository.VideolikeRepository;
import com.osb.youtube.service.interfaces.TrendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrendingServiceImpl implements TrendingService {

    private final VideoRepository videoRepository;
    private final VideoViewRepository videoViewRepository;
    private final VideolikeRepository videoLikeRepository;
    private final CommentRepository commentRepository;
    private final VideoMapper videoMapper;

    @Override
    public List<VideoResponse> getTrendingVideos() {
        List<Video> videos = videoRepository.findAll();
        return videos.stream()
                .sorted(
                        Comparator.comparingDouble(
                                this::calculateTrendingScore
                        ).reversed()
                )
                .limit(20)
                .map(videoMapper::toResponse)
                .toList();
    }

    private double calculateTrendingScore(Video video) {
        long recentViews = getRecentViews(video.getId());
        long likes = videoLikeRepository.countByVideoIdAndStatus(
                video.getId(),
                LikeStatus.LIKE
        );
        long comments = commentRepository.countByVideoId(
                video.getId()
        );
        double recencyScore = getRecencyScore(video);
        return recentViews
                + (likes * 5)
                + (comments * 10)
                + recencyScore;
    }

    private long getRecentViews(String videoId) {
        LocalDateTime last24Hours =
                LocalDateTime.now().minusHours(24);
        return videoViewRepository
                .countByVideoIdAndViewedAtAfter(
                        videoId,
                        last24Hours
                );
    }
    private double getRecencyScore(Video video) {
        LocalDateTime createdAt = LocalDateTime.of(
                video.getDateCreated(),
                video.getTimeCreated()
        );
        long hoursSinceUpload =
                Duration.between(
                        createdAt,
                        LocalDateTime.now()
                ).toHours();
        return 1000.0 / (hoursSinceUpload + 1);
    }
}