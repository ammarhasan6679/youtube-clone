package com.osb.youtube.service.interfaces;

import com.osb.youtube.dto.response.SubscriptionResponse;

import java.util.List;

public interface SubscriptionService {

    void subscribe(String channelId);

    void unsubscribe(String channelId);

    List<SubscriptionResponse> getMySubscriptions();

    long getSubscriberCount(String channelId);

    boolean isSubscribed(String channelId);
}