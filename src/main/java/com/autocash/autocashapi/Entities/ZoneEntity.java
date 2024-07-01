package com.autocash.autocashapi.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ZoneEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Constructors, getters, and setters
    public Long getId() {
        return id;
    }

    // Note: setId method is not needed explicitly for @Id fields with @GeneratedValue

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}