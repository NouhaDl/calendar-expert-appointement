package ma.autocash.booking.api.mapper;

import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.entity.Availability;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AvailabilityMapper {


    AvailabilityDto toDto(Availability availability);


    Availability toEntity(AvailabilityDto availabilityDto);

}
