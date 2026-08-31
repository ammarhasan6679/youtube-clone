package com.osb.youtube.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_users")
public class User extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String displayName;

    @Column(unique = true, nullable = false)
    private String userEmail;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> userComments;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Channel userChannel;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<VideoLike> userLikes;

    @OneToMany(mappedBy = "userHistory", cascade = CascadeType.ALL)
    private List<WatchHistory> userWatchHistories;

    @Column(length = 1000)
    private String description;

    private String profilePicture;

}

