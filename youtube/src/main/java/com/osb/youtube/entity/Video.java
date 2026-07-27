package com.osb.youtube.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "videos")
public class Video extends BaseEntity {

    @Column(length = 100)
    private String videoTitle;

    @Column(length = 10000)
    private String videoDescription;

    @Column(nullable = false)
    private String videoUrl;

    private Integer duration;

    @OneToMany(
            mappedBy = "video",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<VideoLike> videoLikes;
    @OneToMany(
            mappedBy = "video",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<Comment> videoComments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false)
    private Channel channel;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String thumbnailUrl;

    @Column(nullable = false)
    private Integer views = 0;
    @OneToMany(
            mappedBy = "video",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<WatchHistory> watchHistories;

    @OneToMany(
            mappedBy = "video",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<WatchLater> watchLaterList;


}







