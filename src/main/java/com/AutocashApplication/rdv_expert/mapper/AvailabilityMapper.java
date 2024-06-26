package com.AutocashApplication.rdv_expert.mapper;
import com.AutocashApplication.rdv_expert.dto.AvailabilityDto;
import com.AutocashApplication.rdv_expert.entity.Availability;
import com.AutocashApplication.rdv_expert.entity.Expert;
public interface AvailabilityMapper {
    AvailabilityDto toDTO(Availability availability);
    Availability toEntity(AvailabilityDto dto, Expert expert);
}
