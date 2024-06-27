package com.AutocashApplication.rdv_expert.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Data
public class BookingDto {
    private Long id;
    private Long expertId;
    private Long zoneId;
    private LocalDateTime bookingTime;

}
