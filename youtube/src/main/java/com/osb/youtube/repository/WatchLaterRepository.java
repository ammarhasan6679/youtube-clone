package com.osb.youtube.repository;

import com.osb.youtube.entity.WatchLater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchLaterRepository extends JpaRepository<WatchLater,Long> {
}
