package com.osb.youtube.controller;

import com.osb.youtube.dto.request.CreatePlaylistRequest;
import com.osb.youtube.dto.response.PlaylistResponse;
import com.osb.youtube.service.interfaces.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<String> createPlaylist(
            @RequestBody CreatePlaylistRequest request) {

        playlistService.createPlaylist(request);

        return ResponseEntity.ok("Playlist created successfully");
    }

    @GetMapping
    public ResponseEntity<List<PlaylistResponse>> getMyPlaylists() {

        return ResponseEntity.ok(
                playlistService.getMyPlaylists()
        );
    }

    @PostMapping("/{playlistId}/videos/{videoId}")
    public ResponseEntity<String> addVideoToPlaylist(
            @PathVariable String playlistId,
            @PathVariable String videoId) {

        playlistService.addVideoToPlaylist(
                playlistId,
                videoId
        );

        return ResponseEntity.ok(
                "Video added to playlist successfully");
    }

    @DeleteMapping("/{playlistId}/videos/{videoId}")
    public ResponseEntity<String> removeVideoFromPlaylist(
            @PathVariable String playlistId,
            @PathVariable String videoId) {

        playlistService.removeVideoFromPlaylist(
                playlistId,
                videoId
        );

        return ResponseEntity.ok(
                "Video removed from playlist successfully");
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<String> deletePlaylist(
            @PathVariable String playlistId) {

        playlistService.deletePlaylist(playlistId);

        return ResponseEntity.ok(
                "Playlist deleted successfully");
    }
}