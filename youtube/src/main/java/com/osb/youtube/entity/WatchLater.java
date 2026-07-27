package com.osb.youtube.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
        name = "watch_later",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "video_id"})
)
public class WatchLater extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;
}


