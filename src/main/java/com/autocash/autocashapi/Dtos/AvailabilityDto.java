package com.autocash.autocashapi.Dtos;
import java.time.LocalDateTime;

public class AvailabilityDto {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ExpertDto expert;
    private ZoneDto zone;

    // Constructors, getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ExpertDto getExpert() {
        return expert;
    }

    public void setExpert(ExpertDto expert) {
        this.expert = expert;
    }

    public ZoneDto getZone() {
        return zone;
    }

    public void setZone(ZoneDto zone) {
        this.zone = zone;
    }
}