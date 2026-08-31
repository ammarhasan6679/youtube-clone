package com.osb.youtube.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    String uploadFile(MultipartFile file, String folderName);
    void deleteFile(String fileUrl);
}
