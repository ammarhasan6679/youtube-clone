package com.osb.youtube.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistVideo extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;
    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;
}

