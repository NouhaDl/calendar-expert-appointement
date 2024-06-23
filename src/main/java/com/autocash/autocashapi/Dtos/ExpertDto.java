package com.autocash.autocashapi.Dtos;
import java.time.LocalDateTime;
public class ExpertDto {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Autres constructeurs, getters et setters pour les autres champs
}