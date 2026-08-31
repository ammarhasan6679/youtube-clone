package com.osb.youtube.service.interfaces;

public interface ChannelMembershipService {

    void joinChannel(String channelId);

    void leaveChannel(String channelId);

    boolean isMember(String channelId);
}