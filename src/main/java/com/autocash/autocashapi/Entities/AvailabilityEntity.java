package com.autocash.autocashapi.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AvailabilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "expert_id")
    private ExpertEntity expert;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // Constructors
    public AvailabilityEntity() {
        // Default constructor
    }

    public AvailabilityEntity(ExpertEntity expert, LocalDateTime startTime, LocalDateTime endTime) {
        this.expert = expert;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExpertEntity getExpert() {
        return expert;
    }

    public void setExpert(ExpertEntity expert) {
        this.expert = expert;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
