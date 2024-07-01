package ma.autocash.booking.api.services;

import ma.autocash.booking.api.dto.ZoneDto;

import java.util.List;

public interface ZoneService {
    ZoneDto saveZone(ZoneDto zoneDto);
    ZoneDto updateZone(Long id, ZoneDto zoneDto);
    void deleteZone(Long id);
    List<ZoneDto> getAllZones();
    ZoneDto getZoneById(Long id);
}
