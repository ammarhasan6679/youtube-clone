package com.osb.youtube.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistVideoResponse {

    private String videoId;
    private String videoTitle;
    private String thumbnailUrl;
    private String channelName;
}