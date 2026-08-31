package com.osb.youtube.service.impl;

import com.osb.youtube.entity.Channel;
import com.osb.youtube.entity.ChannelMembership;
import com.osb.youtube.entity.User;
import com.osb.youtube.repository.ChannelMembershipRepository;
import com.osb.youtube.repository.ChannelRepository;
import com.osb.youtube.repository.UserRepository;
import com.osb.youtube.service.interfaces.ChannelMembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelMembershipServiceImpl
        implements ChannelMembershipService {
    private final ChannelMembershipRepository channelMembershipRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    @Override
    public void joinChannel(String channelId) {
        User user = getCurrentUser();
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() ->
                        new RuntimeException("Channel not found"));
        if (channel.getUser().getId().equals(user.getId())) {
            throw new RuntimeException(
                    "You cannot join your own channel");
        }
        boolean alreadyMember =
                channelMembershipRepository
                        .findByUserIdAndChannelId(
                                user.getId(),
                                channelId
                        )
                        .isPresent();
        if (alreadyMember) {
            throw new RuntimeException(
                    "Already a member of this channel");
        }
        ChannelMembership membership = new ChannelMembership();
        membership.setUser(user);
        membership.setChannel(channel);
        channelMembershipRepository.save(membership);
    }

    @Override
    public void leaveChannel(String channelId) {
        User user = getCurrentUser();
        ChannelMembership membership =
                channelMembershipRepository
                        .findByUserIdAndChannelId(
                                user.getId(),
                                channelId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "You are not a member of this channel"));
        channelMembershipRepository.delete(membership);
    }

    @Override
    public boolean isMember(String channelId) {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {
            return false;
        }
        User user = getCurrentUser();
        return channelMembershipRepository
                .findByUserIdAndChannelId(
                        user.getId(),
                        channelId
                )
                .isPresent();
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
}