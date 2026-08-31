package com.osb.youtube.repository;

import com.osb.youtube.entity.VideoLike;
import com.osb.youtube.enums.LikeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideolikeRepository extends JpaRepository<VideoLike,String> {
    Optional<VideoLike> findByUserIdAndVideoId(String userId, String videoId);
    long countByVideoIdAndStatus(String videoId, LikeStatus status);

}
