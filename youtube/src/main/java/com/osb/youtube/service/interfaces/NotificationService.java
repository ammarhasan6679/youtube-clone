package com.osb.youtube.service.interfaces;

import com.osb.youtube.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {

    List<NotificationResponse> getNotifications();

    List<NotificationResponse> getUnreadNotifications();

    long getUnreadCount();

    void markAsRead(String notificationId);

    void markAllAsRead();

    void deleteNotification(String notificationId);
}