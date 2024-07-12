package ma.autocash.booking.api.service;

import ma.autocash.booking.api.dto.ZoneDto;
import ma.autocash.booking.api.exception.TechnicalException;

import java.util.List;

public interface ZoneService {
    ZoneDto saveZone(ZoneDto zoneDto) throws TechnicalException;
    ZoneDto updateZone(Long id, ZoneDto zoneDto) throws TechnicalException;
    void deleteZone(Long id) throws TechnicalException;
    List<ZoneDto> getAllZones() throws TechnicalException;
    ZoneDto getZoneById(Long id) throws TechnicalException;
}
