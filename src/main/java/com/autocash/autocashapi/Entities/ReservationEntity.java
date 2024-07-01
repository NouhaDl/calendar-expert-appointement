package com.autocash.autocashapi.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "expert_id")
    private ExpertEntity expert;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private ZoneEntity zone;

    @Column(name = "reservation_time")
    private LocalDateTime reservationTime;

    // Constructors
    public ReservationEntity() {
    }

    public ReservationEntity(ExpertEntity expert, ZoneEntity zone, LocalDateTime reservationTime) {
        this.expert = expert;
        this.zone = zone;
        this.reservationTime = reservationTime;
    }

    // Getters and Setters
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

    public ZoneEntity getZone() {
        return zone;
    }

    public void setZone(ZoneEntity zone) {
        this.zone = zone;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }
}