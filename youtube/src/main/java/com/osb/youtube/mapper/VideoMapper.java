package com.osb.youtube.mapper;

import com.osb.youtube.dto.response.VideoResponse;
import com.osb.youtube.entity.Video;
import com.osb.youtube.service.interfaces.VideoLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoMapper {

    private final VideoLikeService videoLikeService;

    public VideoResponse toResponse(Video video) {

        VideoResponse response = new VideoResponse();

        response.setId(video.getId());
        response.setVideoTitle(video.getVideoTitle());
        response.setVideoDescription(video.getVideoDescription());
        response.setVideoUrl(video.getVideoUrl());
        response.setThumbnailUrl(video.getThumbnailUrl());
        response.setViews(video.getViews());
        response.setDuration(video.getDuration());

        response.setChannelName(
                video.getChannel().getChannelName()
        );

        response.setCategoryName(
                video.getCategory().getCategoryName()
        );

        response.setLikeCount(
                videoLikeService.getLikeCount(video.getId())
        );

        response.setDislikeCount(
                videoLikeService.getDislikeCount(video.getId())
        );
        response.setMembersOnly(video.getMembersOnly());

        return response;
    }
}