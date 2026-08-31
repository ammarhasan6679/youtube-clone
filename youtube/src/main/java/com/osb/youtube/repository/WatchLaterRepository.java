package com.osb.youtube.repository;

import com.osb.youtube.entity.WatchLater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchLaterRepository extends JpaRepository<WatchLater,String> {
    Optional<WatchLater> findByUserIdAndVideoId(
            String userId,
            String videoId
    );

    List<WatchLater> findByUserId(String userId);

    void deleteByUserIdAndVideoId(
            String userId,
            String videoId
    );
}
