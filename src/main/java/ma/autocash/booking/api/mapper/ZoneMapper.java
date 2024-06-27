package com.AutocashApplication.rdv_expert.mapper;

import com.AutocashApplication.rdv_expert.dto.ZoneDto;
import com.AutocashApplication.rdv_expert.entity.Zone;

public interface ZoneMapper {

    ZoneDto toDTO(Zone zone);

    Zone toEntity(ZoneDto dto);
}
