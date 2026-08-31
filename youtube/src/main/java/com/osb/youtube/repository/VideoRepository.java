package com.osb.youtube.repository;

import com.osb.youtube.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video,String> {
    List<Video> findByVideoTitleContainingIgnoreCaseOrVideoDescriptionContainingIgnoreCase(
            String title,
            String description
    );
    List<Video> findByCategoryId(String categoryId);
    List<Video> findByChannelId(String channelId);
    List<Video> findTop20ByOrderByViewsDesc();
}
