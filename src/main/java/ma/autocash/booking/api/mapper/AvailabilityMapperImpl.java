package ma.autocash.booking.api.mapper;

import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.entity.Expert;
import org.mapstruct.MappingTarget;
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
    public Availability toEntity(AvailabilityDto dto, @MappingTarget Availability availability) {
        if (dto == null) {
            return null;
        }

        if (availability == null) {
            availability = new Availability();
        }

        availability.setId(dto.getId());
        availability.setDate(dto.getDate());
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());
        // You may need to set the Expert entity here
        // availability.setExpert(new Expert(dto.getExpertId()));

        return availability;
    }
}
