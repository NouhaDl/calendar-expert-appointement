package ma.autocash.booking.api.mapper;

import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.entity.Availability;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface AvailabilityMapper {

    @Mappings({
            @Mapping(source = "expert.id", target = "expertId"),

    })
    AvailabilityDto toDto(Availability availability);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "expertId", target = "expert.id"),
            @Mapping(target = "expert", ignore = true),
    })
    Availability toEntity(AvailabilityDto availabilityDto);


}
