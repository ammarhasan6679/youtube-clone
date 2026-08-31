package com.osb.youtube.service.impl;

import com.osb.youtube.dto.response.NotificationResponse;
import com.osb.youtube.entity.Notification;
import com.osb.youtube.entity.User;
import com.osb.youtube.repository.NotificationRepository;
import com.osb.youtube.repository.UserRepository;
import com.osb.youtube.service.interfaces.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    @Override
    public List<NotificationResponse> getNotifications() {
        User user = getCurrentUser();
        return notificationRepository
                .findByUserIdOrderByDateCreatedDescTimeCreatedDesc(user.getId())
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public List<NotificationResponse> getUnreadNotifications() {
        User user = getCurrentUser();
        return notificationRepository
                .findByUserIdAndIsReadFalseOrderByDateCreatedDescTimeCreatedDesc(
                        user.getId()
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public long getUnreadCount() {
        User user = getCurrentUser();
        return notificationRepository
                .countByUserIdAndIsReadFalse(user.getId());
    }

    @Override
    public void markAsRead(String notificationId) {
        User user = getCurrentUser();
        Notification notification = notificationRepository
                .findById(notificationId)
                .orElseThrow(() ->
                        new RuntimeException("Notification not found"));
        if (!notification.getUser().getId().equals(user.getId())) {
            throw new RuntimeException(
                    "You cannot modify this notification");
        }
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public void markAllAsRead() {
        User user = getCurrentUser();
        List<Notification> notifications =
                notificationRepository
                        .findByUserIdAndIsReadFalseOrderByDateCreatedDescTimeCreatedDesc(
                                user.getId()
                        );
        notifications.forEach(notification ->
                notification.setIsRead(true)
        );
        notificationRepository.saveAll(notifications);
    }

    @Override
    public void deleteNotification(String notificationId) {
        User user = getCurrentUser();
        Notification notification = notificationRepository
                .findById(notificationId)
                .orElseThrow(() ->
                        new RuntimeException("Notification not found"));
        if (!notification.getUser().getId().equals(user.getId())) {
            throw new RuntimeException(
                    "You cannot delete this notification");
        }
        notificationRepository.delete(notification);
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

    private NotificationResponse convertToResponse(
            Notification notification) {
        NotificationResponse response =
                new NotificationResponse();
        response.setId(notification.getId());
        response.setType(notification.getType());
        response.setMessage(notification.getMessage());
        response.setIsRead(notification.getIsRead());
        response.setDateCreated(notification.getDateCreated());
        response.setTimeCreated(notification.getTimeCreated());
        if (notification.getVideo() != null) {
            response.setVideoId(
                    notification.getVideo().getId()
            );
        }
        if (notification.getChannel() != null) {
            response.setChannelId(
                    notification.getChannel().getId()
            );
        }

        return response;
    }
}