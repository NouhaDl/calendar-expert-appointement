package ma.autocash.booking.api.mapper;

import ma.autocash.booking.api.dto.ExpertDto;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Zone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpertMapper {

    ExpertMapper INSTANCE = Mappers.getMapper(ExpertMapper.class);

    @Mappings({
            @Mapping(source = "zones", target = "zoneIds")
    })
    ExpertDto toDTO(Expert expert);

    @Mappings({
            @Mapping(source = "zoneIds", target = "zones")
    })
    Expert toEntity(ExpertDto dto, List<Zone> zones);

    void updateFromDto(ExpertDto dto, Expert expert, List<Zone> zones);
}
