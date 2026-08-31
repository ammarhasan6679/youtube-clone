package com.osb.youtube.repository;

import com.osb.youtube.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
    Optional<Subscription> findBySubscriberIdAndChannelId(
            String userId,
            String channelId
    );

    List<Subscription> findBySubscriberId(String userId);

    List<Subscription> findByChannelId(String channelId);

    long countByChannelId(String channelId);

}
