package com.AutocashApplication.rdv_expert.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Data
public class ZoneDto {

    private Long id;
    private String name;
    private List<Long> expertIds;
    private List<Long> bookingIds;

    // Constructor with id and name
    public ZoneDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }



}
