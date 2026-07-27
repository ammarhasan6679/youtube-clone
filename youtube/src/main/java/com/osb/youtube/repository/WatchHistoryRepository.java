package com.osb.youtube.repository;

import com.osb.youtube.entity.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchHistoryRepository extends JpaRepository<WatchHistory,Long> {
}
