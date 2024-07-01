package com.autocash.autocashapi.Entities;

import jakarta.persistence.*;

@Entity
public class ExpertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private ZoneEntity zone;

    // Constructors, getters, and setters
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setZone(ZoneEntity zone) {
        this.zone = zone;
    }

    public ZoneEntity getZone() {
        return zone;
    }


    // Constructors, getters, and setters for other fields
}