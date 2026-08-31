package com.osb.youtube.repository;

import com.osb.youtube.entity.ChannelMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ChannelMembershipRepository
        extends JpaRepository<ChannelMembership,String> {
    Optional<ChannelMembership> findByUserIdAndChannelId(
            String userId,
            String channelId
    );
}
