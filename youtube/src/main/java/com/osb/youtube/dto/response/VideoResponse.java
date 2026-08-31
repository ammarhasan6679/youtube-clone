package com.osb.youtube.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoResponse {

    private String id;
    private String videoTitle;
    private String videoDescription;
    private String videoUrl;
    private String thumbnailUrl;
    private Integer views;
    private Integer duration;
    private String channelId;
    private String channelName;
    private String categoryName;
    private Long likeCount;
    private Long dislikeCount;
    private Boolean membersOnly;
}