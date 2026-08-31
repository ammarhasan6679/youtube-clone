package com.osb.youtube.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionResponse {

    private String channelId;
    private String channelName;
    private String profileImageUrl;
    private long subscriberCount;
}