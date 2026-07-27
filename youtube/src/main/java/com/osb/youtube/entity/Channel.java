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
public class Channel extends BaseEntity {
    @Column(nullable = false)
    private String channelName;
    @Column(length = 1000)
    private String description;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    @OneToMany(mappedBy = "channel")
    private List<Video> videos;
    private String profileImageUrl;
}



