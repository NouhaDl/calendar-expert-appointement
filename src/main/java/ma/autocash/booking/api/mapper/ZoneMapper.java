package ma.autocash.booking.api.mapper;

import ma.autocash.booking.api.dto.ZoneDto;
import ma.autocash.booking.api.entity.Zone;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ZoneMapper {

    ZoneDto toDto(Zone zone);

    Zone toEntity(ZoneDto dto);

}
