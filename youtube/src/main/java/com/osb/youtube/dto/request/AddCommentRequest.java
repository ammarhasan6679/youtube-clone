package com.osb.youtube.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCommentRequest {

    private String videoId;
    private String text;
    private String parentCommentId;
}
