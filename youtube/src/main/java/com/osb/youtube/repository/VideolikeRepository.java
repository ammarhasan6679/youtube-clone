package com.osb.youtube.repository;

import com.osb.youtube.entity.VideoLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideolikeRepository extends JpaRepository<VideoLike,Long> {
}
