package ma.autocash.booking.api.mapper;

import ma.autocash.booking.api.dto.ZoneDto;
import ma.autocash.booking.api.entity.Zone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ZoneMapper {

    ZoneMapper INSTANCE = Mappers.getMapper(ZoneMapper.class);

    ZoneDto toDto(Zone zone);

    @Mappings({
            @Mapping(target = "id", ignore = true), // Ignore mapping of id during entity creation
            @Mapping(target = "experts", ignore = true), // Ignore mapping of experts for now
            @Mapping(target = "bookings", ignore = true) // Ignore mapping of bookings for now
    })
    Zone toEntity(ZoneDto dto);

    @Mappings({
            @Mapping(target = "id", ignore = true), // Ignore mapping of id
            @Mapping(target = "name", source = "dto.name"), // Map name from dto
            @Mapping(target = "experts", ignore = true), // Ignore mapping of experts for now
            @Mapping(target = "bookings", ignore = true) // Ignore mapping of bookings for now
    })
    void updateFromDto(ZoneDto dto, Zone zone);
}
