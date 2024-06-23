package com.autocash.autocashapi.Mappers;
import com.autocash.autocashapi.Dtos.ExpertDto;
import com.autocash.autocashapi.Entities.ExpertEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpertMapper {
    ExpertDto toDto(ExpertEntity expertEntity);

    ExpertEntity toEntity(ExpertDto expertDto);

    // Ajoutez d'autres méthodes de mappage si nécessaire
}
