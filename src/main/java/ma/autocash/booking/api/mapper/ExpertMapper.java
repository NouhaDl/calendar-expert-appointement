package ma.autocash.booking.api.mapper;

import ma.autocash.booking.api.dto.ExpertDto;
import ma.autocash.booking.api.entity.Expert;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpertMapper {
    ExpertDto toDto(Expert expert);

    Expert toEntity(ExpertDto expertDto);
    List<ExpertDto> toDtoList(List<Expert> experts);

}

