package com.osb.youtube.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WatchHistoryResponse {

    private String videoId;
    private String videoTitle;
    private String thumbnailUrl;
    private String channelName;
    private Integer duration;
    private Integer views;
    private LocalDateTime watchedAt;
}