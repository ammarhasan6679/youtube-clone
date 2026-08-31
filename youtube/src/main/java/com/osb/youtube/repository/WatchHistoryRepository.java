package com.osb.youtube.repository;

import com.osb.youtube.entity.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchHistoryRepository extends JpaRepository<WatchHistory,String> {
    Optional<WatchHistory> findByUserHistoryIdAndVideoId(
            String userId,
            String videoId
    );

    List<WatchHistory> findByUserHistoryIdOrderByWatchedAtDesc(
            String userId
    );

    void deleteByUserHistoryIdAndVideoId(
            String userId,
            String videoId
    );

    void deleteByUserHistoryId(String userId);
}
