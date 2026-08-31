package com.osb.youtube.service.interfaces;

import com.osb.youtube.enums.LikeStatus;

public interface VideoLikeService {
    void reactToVideo(String videoId, LikeStatus status);
    long getLikeCount(String videoId);
    long getDislikeCount(String videoId);
    void removeReaction(String videoId);

}
