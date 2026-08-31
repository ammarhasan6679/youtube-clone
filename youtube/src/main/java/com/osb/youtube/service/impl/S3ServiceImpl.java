package com.osb.youtube.service.impl;

import com.osb.youtube.service.interfaces.S3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;
    @Value("${aws.s3.bucket}")
    private String bucketName;
    public S3ServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }
    @Override
    public String uploadFile(MultipartFile file, String folderName) {
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID() + extension;
        String key = folderName + "/" + fileName;
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType("image/jpeg")
                    .build();
            s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromBytes(file.getBytes())
            );
            return s3Client.utilities()
                    .getUrl(builder -> builder.bucket(bucketName).key(key))
                    .toExternalForm();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }
    @Override
    public void deleteFile(String fileUrl) {
        String key = fileUrl;
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.deleteObject(request);
    }
}