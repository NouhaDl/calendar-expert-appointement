package ma.autocash.booking.api.mapper;

import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.entity.Availability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AvailabilityMapper {

    AvailabilityMapper INSTANCE = Mappers.getMapper(AvailabilityMapper.class);

    @Mapping(source = "expert.id", target = "expertId")
    AvailabilityDto toDTO(Availability availability);

    @Mapping(source = "expertId", target = "expert", ignore = true)
    Availability toEntity(AvailabilityDto dto, @MappingTarget Availability availability);
}
