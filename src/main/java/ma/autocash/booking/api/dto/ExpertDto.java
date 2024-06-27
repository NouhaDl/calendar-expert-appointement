package com.AutocashApplication.rdv_expert.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@Data
public class ExpertDto {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Long> zoneIds;
    private List<Long> availabilityIds;
    private List<Long> bookingIds;

}