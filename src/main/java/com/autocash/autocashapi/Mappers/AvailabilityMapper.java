package com.autocash.autocashapi.Mappers;

import com.autocash.autocashapi.Dtos.AvailabilityDto;
import com.autocash.autocashapi.Entities.AvailabilityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ExpertMapper.class, ZoneMapper.class})
public interface AvailabilityMapper {

    @Mapping(source = "expert", target = "expert")
    @Mapping(target = "zone", ignore = true)
    AvailabilityDto toDto(AvailabilityEntity availabilityEntity);

    @Mapping(source = "expert", target = "expert")
    @Mapping(target = "zone", ignore = true)
    default AvailabilityEntity toEntity(AvailabilityDto availabilityDto) {
        return null;
    }

    // Ajoutez d'autres méthodes de mappage si nécessaire
}
