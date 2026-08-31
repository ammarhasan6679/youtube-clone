package com.osb.youtube.service.impl;

import com.osb.youtube.dto.response.WatchLaterResponse;
import com.osb.youtube.entity.User;
import com.osb.youtube.entity.Video;
import com.osb.youtube.entity.WatchLater;
import com.osb.youtube.repository.UserRepository;
import com.osb.youtube.repository.VideoRepository;
import com.osb.youtube.repository.WatchLaterRepository;
import com.osb.youtube.service.interfaces.WatchLaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchLaterServiceImpl implements WatchLaterService {

    private final WatchLaterRepository watchLaterRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    @Override
    public void addToWatchLater(String videoId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));
        boolean alreadyExists = watchLaterRepository
                .findByUserIdAndVideoId(user.getId(), videoId)
                .isPresent();
        if (alreadyExists) {
            throw new RuntimeException("Video already in Watch Later");
        }
        WatchLater watchLater = new WatchLater();
        watchLater.setUser(user);
        watchLater.setVideo(video);
        watchLaterRepository.save(watchLater);
    }

    @Override
    public void removeFromWatchLater(String videoId) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        WatchLater watchLater = watchLaterRepository
                .findByUserIdAndVideoId(user.getId(), videoId)
                .orElseThrow(() ->
                        new RuntimeException("Video not found in Watch Later"));
        watchLaterRepository.delete(watchLater);
    }

    @Override
    public List<WatchLaterResponse> getWatchLater() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<WatchLater> watchLaterList =
                watchLaterRepository.findByUserId(user.getId());
        return watchLaterList.stream()
                .map(this::convertToResponse)
                .toList();
    }

    private WatchLaterResponse convertToResponse(WatchLater watchLater) {
        Video video = watchLater.getVideo();
        WatchLaterResponse response = new WatchLaterResponse();
        response.setVideoId(video.getId());
        response.setVideoTitle(video.getVideoTitle());
        response.setThumbnailUrl(video.getThumbnailUrl());
        response.setChannelName(video.getChannel().getChannelName());
        response.setDuration(video.getDuration());
        response.setViews(video.getViews());
        return response;
    }
}