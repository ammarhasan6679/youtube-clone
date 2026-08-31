package com.osb.youtube.repository;

import com.osb.youtube.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository
        extends JpaRepository<Notification, String> {
    List<Notification> findByUserIdOrderByDateCreatedDescTimeCreatedDesc(
            String userId
    );

    List<Notification> findByUserIdAndIsReadFalseOrderByDateCreatedDescTimeCreatedDesc(
            String userId
    );

    long countByUserIdAndIsReadFalse(String userId);
}
