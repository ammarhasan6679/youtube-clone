package com.osb.youtube.service.impl;

import com.osb.youtube.dto.response.SubscriptionResponse;
import com.osb.youtube.entity.Channel;
import com.osb.youtube.entity.Subscription;
import com.osb.youtube.entity.User;
import com.osb.youtube.repository.ChannelRepository;
import com.osb.youtube.repository.SubscriptionRepository;
import com.osb.youtube.repository.UserRepository;
import com.osb.youtube.service.interfaces.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    @Override
    public void subscribe(String channelId) {
        User user = getCurrentUser();
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        if (channel.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot subscribe to your own channel");
        }
        boolean alreadySubscribed = subscriptionRepository
                .findBySubscriberIdAndChannelId(user.getId(), channelId)
                .isPresent();
        if (alreadySubscribed) {
            throw new RuntimeException("Already subscribed");
        }
        Subscription subscription = new Subscription();
        subscription.setSubscriber(user);
        subscription.setChannel(channel);
        subscriptionRepository.save(subscription);
    }

    @Override
    public void unsubscribe(String channelId) {
        User user = getCurrentUser();
        Subscription subscription = subscriptionRepository
                .findBySubscriberIdAndChannelId(
                        user.getId(),
                        channelId
                )
                .orElseThrow(() ->
                        new RuntimeException("Not subscribed to this channel"));
        subscriptionRepository.delete(subscription);
    }

    @Override
    public List<SubscriptionResponse> getMySubscriptions() {
        User user = getCurrentUser();
        List<Subscription> subscriptions =
                subscriptionRepository.findBySubscriberId(user.getId());
        return subscriptions.stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public long getSubscriberCount(String channelId) {
        channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        return subscriptionRepository.countByChannelId(channelId);
    }

    @Override
    public boolean isSubscribed(String channelId) {
        User user = getCurrentUser();
        return subscriptionRepository
                .findBySubscriberIdAndChannelId(
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
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private SubscriptionResponse convertToResponse(
            Subscription subscription) {
        Channel channel = subscription.getChannel();
        SubscriptionResponse response = new SubscriptionResponse();
        response.setChannelId(channel.getId());
        response.setChannelName(channel.getChannelName());
        response.setProfileImageUrl(channel.getProfileImageUrl());
        response.setSubscriberCount(
                subscriptionRepository.countByChannelId(channel.getId())
        );
        return response;
    }
}