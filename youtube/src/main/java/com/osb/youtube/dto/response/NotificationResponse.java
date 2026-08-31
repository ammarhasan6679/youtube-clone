package com.osb.youtube.dto.response;

import com.osb.youtube.enums.NotificationType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class NotificationResponse {

    private String id;
    private NotificationType type;
    private String message;
    private String videoId;
    private String channelId;
    private Boolean isRead;
    private LocalDate dateCreated;
    private LocalTime timeCreated;
}