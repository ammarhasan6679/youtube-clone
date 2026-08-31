package com.osb.youtube.dto.request;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class VideoUploadRequest {

    private String videoTitle;
    private String videoDescription;
    private String categoryId;
    private MultipartFile videoFile;
    private MultipartFile thumbnailFile;
    private Boolean membersOnly;
}

