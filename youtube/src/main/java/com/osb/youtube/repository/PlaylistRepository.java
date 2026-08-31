package com.osb.youtube.repository;

import com.osb.youtube.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, String> {
    List<Playlist> findByUserId(String userId);
}