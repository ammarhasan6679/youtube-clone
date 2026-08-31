package com.osb.youtube.entity;

import com.osb.youtube.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "video_likes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "video_id"})
        }
)
public class VideoLike extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;
    @Enumerated(EnumType.STRING)
    private LikeStatus status;
}
