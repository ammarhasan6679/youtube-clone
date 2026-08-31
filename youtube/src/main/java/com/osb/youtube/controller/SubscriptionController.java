package com.osb.youtube.controller;

import com.osb.youtube.dto.response.SubscriptionResponse;
import com.osb.youtube.service.interfaces.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/{channelId}")
    public ResponseEntity<String> subscribe(
            @PathVariable String channelId) {
        subscriptionService.subscribe(channelId);
        return ResponseEntity.ok("Subscribed successfully");
    }

    @DeleteMapping("/{channelId}")
    public ResponseEntity<String> unsubscribe(
            @PathVariable String channelId) {
        subscriptionService.unsubscribe(channelId);
        return ResponseEntity.ok("Unsubscribed successfully");
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionResponse>> getMySubscriptions() {
        return ResponseEntity.ok(
                subscriptionService.getMySubscriptions()
        );
    }

    @GetMapping("/{channelId}/count")
    public ResponseEntity<Long> getSubscriberCount(
            @PathVariable String channelId) {
        return ResponseEntity.ok(
                subscriptionService.getSubscriberCount(channelId)
        );
    }

    @GetMapping("/{channelId}/status")
    public ResponseEntity<Boolean> isSubscribed(
            @PathVariable String channelId) {
        return ResponseEntity.ok(
                subscriptionService.isSubscribed(channelId)
        );
    }
}