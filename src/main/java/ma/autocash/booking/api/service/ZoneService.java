package ma.autocash.booking.api.service;

import ma.autocash.booking.api.dto.ZoneDto;
import ma.autocash.booking.api.entity.Zone;

import java.util.List;

public interface ZoneService {
    ZoneDto saveZone(ZoneDto zoneDto);
    ZoneDto updateZone(Long id, ZoneDto zoneDto);
    void deleteZone(Long id);
    List<ZoneDto> getAllZones();
    ZoneDto getZoneById(Long id);
}
