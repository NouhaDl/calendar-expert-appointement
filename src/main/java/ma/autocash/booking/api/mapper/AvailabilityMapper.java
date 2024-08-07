package ma.autocash.booking.api.mapper;

import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.dto.AvailabilityResponseDto;
import ma.autocash.booking.api.entity.Availability;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AvailabilityMapper {


    AvailabilityDto toDto(Availability availability);

    AvailabilityResponseDto toResponseDto(Availability availability);

    Availability toEntity(AvailabilityDto availabilityDto);

    Availability toResponseEntity(AvailabilityResponseDto availabilityResponseDto);

    List<AvailabilityDto> toDto(List<Availability> availabilities);

    List<AvailabilityResponseDto> toResponseDto(List<Availability> availabilities);


}
