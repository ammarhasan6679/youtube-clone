package com.osb.youtube.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaylistResponse {

    private String id;
    private String name;
    private List<PlaylistVideoResponse> videos;
}