package com.osb.youtube.service.interfaces;

import com.osb.youtube.dto.request.CreatePlaylistRequest;
import com.osb.youtube.dto.response.PlaylistResponse;

import java.util.List;

public interface PlaylistService {

    void createPlaylist(CreatePlaylistRequest request);

    List<PlaylistResponse> getMyPlaylists();

    void addVideoToPlaylist(String playlistId, String videoId);

    void removeVideoFromPlaylist(String playlistId, String videoId);

    void deletePlaylist(String playlistId);
}