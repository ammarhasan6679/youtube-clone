package com.osb.youtube.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateCreated;
    private LocalTime timeCreated;
    private LocalDate dateUpdated;
    private LocalTime timeUpdated;
    private Boolean isDeleted;
    @PrePersist
    public void prePersist() {
        this.dateCreated = LocalDate.now();
        this.timeCreated = LocalTime.now();
        this.isDeleted = false;
    }
    @PreUpdate
    public void preUpdate() {
        this.dateUpdated = LocalDate.now();
        this.timeUpdated = LocalTime.now();
    }
}
