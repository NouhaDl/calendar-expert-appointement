package ma.autocash.booking.api.mapper;

import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.entity.Availability;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AvailabilityMapper {


    AvailabilityDto toDto(Availability availability);

    Availability toEntity(AvailabilityDto availabilityDto);

    List<AvailabilityDto> toDto(List<Availability> availabilities);


}
