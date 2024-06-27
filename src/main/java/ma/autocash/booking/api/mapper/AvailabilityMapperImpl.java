package com.AutocashApplication.rdv_expert.mapper;

import com.AutocashApplication.rdv_expert.dto.AvailabilityDto;
import com.AutocashApplication.rdv_expert.entity.Availability;
import com.AutocashApplication.rdv_expert.entity.Expert;
import org.springframework.stereotype.Component;
@Component
public class AvailabilityMapperImpl implements AvailabilityMapper {

    @Override
    public AvailabilityDto toDTO(Availability availability) {
        if (availability == null) {
            return null;
        }

        AvailabilityDto dto = new AvailabilityDto();
        dto.setId(availability.getId());
        dto.setExpertId(availability.getExpert().getId()); // Assuming getId() retrieves the ID of Expert entity
        dto.setDate(availability.getDate());
        dto.setStartTime(availability.getStartTime());
        dto.setEndTime(availability.getEndTime());


        return dto;
    }

    @Override
    public Availability toEntity(AvailabilityDto dto, Expert expert) {
        if (dto == null) {
            return null;
        }

        Availability availability = new Availability();
        availability.setId(dto.getId());
        availability.setExpert(expert);
        availability.setDate(dto.getDate());
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());

        return availability;
    }
}
