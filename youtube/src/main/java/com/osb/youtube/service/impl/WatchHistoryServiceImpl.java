package com.osb.youtube.service.impl;

import com.osb.youtube.dto.response.WatchHistoryResponse;
import com.osb.youtube.entity.User;
import com.osb.youtube.entity.Video;
import com.osb.youtube.entity.WatchHistory;
import com.osb.youtube.exception.UserNotFoundException;
import com.osb.youtube.exception.VideoNotFoundException;
import com.osb.youtube.repository.UserRepository;
import com.osb.youtube.repository.VideoRepository;
import com.osb.youtube.repository.WatchHistoryRepository;
import com.osb.youtube.service.interfaces.WatchHistoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WatchHistoryServiceImpl implements WatchHistoryService {

    private final WatchHistoryRepository watchHistoryRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    @Override
    public void addToHistory(String videoId) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return;
        }
        String username = authentication.getName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));
        WatchHistory history = watchHistoryRepository
                .findByUserHistoryIdAndVideoId(user.getId(), videoId)
                .orElse(null);
        if (history == null) {
            history = new WatchHistory();
            history.setUserHistory(user);
            history.setVideo(video);
        }
        history.setWatchedAt(LocalDateTime.now());
        watchHistoryRepository.save(history);
    }

    @Override
    public List<WatchHistoryResponse> getHistory() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<WatchHistory> history =
                watchHistoryRepository
                        .findByUserHistoryIdOrderByWatchedAtDesc(user.getId());
        return history.stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public void removeFromHistory(String videoId) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        watchHistoryRepository.deleteByUserHistoryIdAndVideoId(
                user.getId(),
                videoId
        );
    }

    @Override
    public void clearHistory() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        watchHistoryRepository.deleteByUserHistoryId(user.getId());
    }

    private WatchHistoryResponse convertToResponse(WatchHistory history) {
        Video video = history.getVideo();
        WatchHistoryResponse response = new WatchHistoryResponse();
        response.setVideoId(video.getId());
        response.setVideoTitle(video.getVideoTitle());
        response.setThumbnailUrl(video.getThumbnailUrl());
        response.setChannelName(video.getChannel().getChannelName());
        response.setDuration(video.getDuration());
        response.setViews(video.getViews());
        response.setWatchedAt(history.getWatchedAt());
        return response;
    }
}