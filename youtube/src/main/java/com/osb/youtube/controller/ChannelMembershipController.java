package com.osb.youtube.controller;

import com.osb.youtube.service.interfaces.ChannelMembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/channel-memberships")
@RequiredArgsConstructor
public class ChannelMembershipController {

    private final ChannelMembershipService channelMembershipService;

    @PostMapping("/{channelId}")
    public ResponseEntity<String> joinChannel(
            @PathVariable String channelId) {
        channelMembershipService.joinChannel(channelId);
        return ResponseEntity.ok(
                "Joined channel successfully");
    }

    @DeleteMapping("/{channelId}")
    public ResponseEntity<String> leaveChannel(
            @PathVariable String channelId) {
        channelMembershipService.leaveChannel(channelId);
        return ResponseEntity.ok(
                "Left channel successfully");
    }

    @GetMapping("/{channelId}/status")
    public ResponseEntity<Boolean> isMember(
            @PathVariable String channelId) {
        return ResponseEntity.ok(
                channelMembershipService.isMember(channelId));
    }
}