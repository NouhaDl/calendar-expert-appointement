package com.AutocashApplication.rdv_expert.mapper;

import com.AutocashApplication.rdv_expert.dto.ExpertDto;
import com.AutocashApplication.rdv_expert.entity.Expert;
import com.AutocashApplication.rdv_expert.entity.Zone;

import java.util.List;

public interface ExpertMapper {

    ExpertDto toDTO(Expert expert);

    Expert toEntity(ExpertDto dto, List<Zone> zones);
}
