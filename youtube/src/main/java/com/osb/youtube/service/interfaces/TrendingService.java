package com.osb.youtube.service.interfaces;

import com.osb.youtube.dto.response.VideoResponse;

import java.util.List;

public interface TrendingService {

    List<VideoResponse> getTrendingVideos();
}