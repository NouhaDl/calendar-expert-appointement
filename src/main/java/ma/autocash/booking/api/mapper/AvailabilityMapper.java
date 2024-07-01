package ma.autocash.booking.api.mapper;

import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.entity.Expert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AvailabilityMapper {

    AvailabilityMapper INSTANCE = Mappers.getMapper(AvailabilityMapper.class);

    @Mappings({
            @Mapping(source = "expert.id", target = "expertId"),
            // Map other fields as needed
    })
    AvailabilityDto toDto(Availability availability);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "expertId", target = "expert.id"),
            @Mapping(target = "expert", ignore = true), // Ignore expert mapping here
            // Map other fields as needed
    })
    Availability toEntity(AvailabilityDto availabilityDto);

    void updateFromDto(AvailabilityDto availabilityDto, @MappingTarget Availability availability);

    default Availability toEntityWithExpert(AvailabilityDto availabilityDto, Expert expert) {
        Availability availability = toEntity(availabilityDto);
        availability.setExpert(expert);
        return availability;
    }
}
