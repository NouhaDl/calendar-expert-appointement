package com.AutocashApplication.rdv_expert.entity;

import lombok.Data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "expert_id")
    private Expert expert;


    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;


    private LocalDateTime bookingTime;

}
