package com.osb.youtube.service.interfaces;

import com.osb.youtube.dto.response.WatchHistoryResponse;

import java.util.List;

public interface WatchHistoryService {

    void addToHistory(String videoId);

    List<WatchHistoryResponse> getHistory();

    void removeFromHistory(String videoId);

    void clearHistory();
}