package com.osb.youtube.service.impl;

import com.osb.youtube.dto.request.CreatePlaylistRequest;
import com.osb.youtube.dto.response.PlaylistResponse;
import com.osb.youtube.dto.response.PlaylistVideoResponse;
import com.osb.youtube.entity.Playlist;
import com.osb.youtube.entity.PlaylistVideo;
import com.osb.youtube.entity.User;
import com.osb.youtube.entity.Video;
import com.osb.youtube.repository.PlaylistRepository;
import com.osb.youtube.repository.PlaylistVideoRepository;
import com.osb.youtube.repository.UserRepository;
import com.osb.youtube.repository.VideoRepository;
import com.osb.youtube.service.interfaces.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistVideoRepository playlistVideoRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    @Override
    public void createPlaylist(CreatePlaylistRequest request) {
        User user = getCurrentUser();
        Playlist playlist = new Playlist();
        playlist.setName(request.getName());
        playlist.setUser(user);
        playlistRepository.save(playlist);
    }

    @Override
    public List<PlaylistResponse> getMyPlaylists() {
        User user = getCurrentUser();
        List<Playlist> playlists =
                playlistRepository.findByUserId(user.getId());
        return playlists.stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public void addVideoToPlaylist(
            String playlistId,
            String videoId) {
        User user = getCurrentUser();
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() ->
                        new RuntimeException("Playlist not found"));
        if (!playlist.getUser().getId().equals(user.getId())) {
            throw new RuntimeException(
                    "You can modify only your own playlist");
        }
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() ->
                        new RuntimeException("Video not found"));
        boolean alreadyExists =
                playlistVideoRepository
                        .findByPlaylistIdAndVideoId(
                                playlistId,
                                videoId)
                        .isPresent();
        if (alreadyExists) {
            throw new RuntimeException(
                    "Video already exists in playlist");
        }
        PlaylistVideo playlistVideo = new PlaylistVideo();
        playlistVideo.setPlaylist(playlist);
        playlistVideo.setVideo(video);
        playlistVideoRepository.save(playlistVideo);
    }

    @Override
    public void removeVideoFromPlaylist(
            String playlistId,
            String videoId) {
        User user = getCurrentUser();
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() ->
                        new RuntimeException("Playlist not found"));
        if (!playlist.getUser().getId().equals(user.getId())) {
            throw new RuntimeException(
                    "You can modify only your own playlist");
        }
        PlaylistVideo playlistVideo =
                playlistVideoRepository
                        .findByPlaylistIdAndVideoId(
                                playlistId,
                                videoId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Video not found in playlist"));
        playlistVideoRepository.delete(playlistVideo);
    }

    @Override
    public void deletePlaylist(String playlistId) {
        User user = getCurrentUser();
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() ->
                        new RuntimeException("Playlist not found"));
        if (!playlist.getUser().getId().equals(user.getId())) {
            throw new RuntimeException(
                    "You can delete only your own playlist");
        }
        playlistRepository.delete(playlist);
    }

    private User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUserName(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    private PlaylistResponse convertToResponse(
            Playlist playlist) {
        PlaylistResponse response = new PlaylistResponse();
        response.setId(playlist.getId());
        response.setName(playlist.getName());
        List<PlaylistVideoResponse> videos =
                playlist.getPlaylistVideos()
                        .stream()
                        .map(this::convertVideoToResponse)
                        .toList();
        response.setVideos(videos);
        return response;
    }

    private PlaylistVideoResponse convertVideoToResponse(
            PlaylistVideo playlistVideo) {
        Video video = playlistVideo.getVideo();
        PlaylistVideoResponse response =
                new PlaylistVideoResponse();
        response.setVideoId(video.getId());
        response.setVideoTitle(video.getVideoTitle());
        response.setThumbnailUrl(video.getThumbnailUrl());
        response.setChannelName(
                video.getChannel().getChannelName());
        return response;
    }
}