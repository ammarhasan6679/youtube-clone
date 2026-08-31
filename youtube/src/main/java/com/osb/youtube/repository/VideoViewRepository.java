package com.osb.youtube.repository;

import com.osb.youtube.entity.VideoView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface VideoViewRepository extends JpaRepository<VideoView, String> {

    long countByVideoIdAndViewedAtAfter(
            String videoId,
            LocalDateTime time
    );



}