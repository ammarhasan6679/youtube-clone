package com.osb.youtube.dto.request;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String displayName;
    private String description;
    private String profilePicture;
}
