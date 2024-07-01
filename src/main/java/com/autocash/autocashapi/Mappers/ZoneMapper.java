package com.autocash.autocashapi.Mappers;
import com.autocash.autocashapi.Dtos.ZoneDto;
import com.autocash.autocashapi.Entities.ZoneEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ZoneMapper {

    ZoneDto toDto(ZoneEntity zoneEntity);

    ZoneEntity toEntity(ZoneDto zoneDto);

    // Ajoutez d'autres méthodes de mappage si nécessaire
}

