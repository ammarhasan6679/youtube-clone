package com.osb.youtube.repository;

import com.osb.youtube.entity.PlaylistVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface PlaylistVideoRepository
        extends JpaRepository<PlaylistVideo, String> {

    Optional<PlaylistVideo> findByPlaylistIdAndVideoId(
            String playlistId,
            String videoId
    );

    List<PlaylistVideo> findByPlaylistId(String playlistId);
}