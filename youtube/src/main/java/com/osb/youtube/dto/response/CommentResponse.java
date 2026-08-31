package com.osb.youtube.dto.response;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommentResponse {

    private String id;
    private String text;
    private String username;
    private List<CommentResponse> replies;
}
