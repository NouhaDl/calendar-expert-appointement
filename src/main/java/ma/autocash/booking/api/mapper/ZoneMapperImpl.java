package com.AutocashApplication.rdv_expert.mapper;

import com.AutocashApplication.rdv_expert.dto.ZoneDto;
import com.AutocashApplication.rdv_expert.entity.Zone;
import org.springframework.stereotype.Component;

@Component
public class ZoneMapperImpl implements ZoneMapper {

    @Override
    public ZoneDto toDTO(Zone zone) {
        if (zone == null) {
            return null;
        }
        return new ZoneDto(zone.getId(), zone.getName());
    }

    @Override
    public Zone toEntity(ZoneDto dto) {
        if (dto == null) {
            return null;
        }
        Zone zone = new Zone();
        zone.setId(dto.getId());
        zone.setName(dto.getName());
        return zone;
    }
}
