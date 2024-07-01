package com.autocash.autocashapi.Mappers;

import com.autocash.autocashapi.Dtos.ReservationDto;
import com.autocash.autocashapi.Entities.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ExpertMapper.class)
public interface ReservationMapper {

    @Mapping(source = "expert.id", target = "expert.id") // Mapping de l'ID de l'expert
    ReservationDto toDto(ReservationEntity reservationEntity);

    @Mapping(source = "expert.id", target = "expert.id") // Mapping de l'ID de l'expert
    ReservationEntity toEntity(ReservationDto reservationDto);

    // Ajoutez d'autres méthodes de mappage si nécessaire
}
