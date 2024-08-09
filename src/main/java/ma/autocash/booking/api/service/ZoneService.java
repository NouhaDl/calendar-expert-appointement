package ma.autocash.booking.api.service;

import ma.autocash.booking.api.dto.ZoneDto;
import ma.autocash.booking.api.exception.BusinessException;

import java.util.List;

public interface ZoneService {

    void saveZone(ZoneDto zoneDto) throws BusinessException;

    void updateZone(Long id, ZoneDto zoneDto) throws BusinessException;

    void deleteZone(Long id) throws BusinessException;

    List<ZoneDto> getAllZones() throws BusinessException;

    ZoneDto getZoneById(Long id) throws BusinessException;
}
