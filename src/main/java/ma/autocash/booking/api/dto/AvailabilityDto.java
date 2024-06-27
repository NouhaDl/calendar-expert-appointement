package com.AutocashApplication.rdv_expert.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Setter
@Getter
@Data
public class AvailabilityDto {
    private Long id;
    private Long expertId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
