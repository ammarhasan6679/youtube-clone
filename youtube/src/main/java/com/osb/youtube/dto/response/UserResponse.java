package com.osb.youtube.dto.response;

import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String username;
    private String displayName;
    private String email;
    private String profilePicture;
    private String bannerImage;
    private String description;
    private String createdAt;
}
