package com.osb.youtube.service.interfaces;

import com.osb.youtube.dto.response.WatchLaterResponse;

import java.util.List;

public interface WatchLaterService {

    void addToWatchLater(String videoId);

    void removeFromWatchLater(String videoId);

    List<WatchLaterResponse> getWatchLater();
}