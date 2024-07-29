package ma.autocash.booking.api.service.impl;

import jakarta.validation.Valid;
import ma.autocash.booking.api.dto.ZoneDto;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.mapper.ZoneMapper;
import ma.autocash.booking.api.provider.ZoneProvider;
import ma.autocash.booking.api.service.ZoneService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ZoneServiceImpl implements ZoneService {

    private final ZoneProvider zoneProvider;
    private final ZoneMapper zoneMapper;

    public ZoneServiceImpl(ZoneProvider zoneProvider, ZoneMapper zoneMapper) {
        this.zoneProvider = zoneProvider;
        this.zoneMapper = zoneMapper;
    }

    @Override
    public void saveZone(@Valid ZoneDto zoneDto) throws BusinessException {
        Zone zone = zoneMapper.toEntity(zoneDto);
        zoneProvider.saveZone(zone);
    }

    @Override
    public void updateZone(Long id, @Valid ZoneDto zoneDto) throws BusinessException {
        Zone existingZone = zoneProvider.getZoneById(id);
        existingZone.setName(zoneDto.getName());
        zoneProvider.updateZone(existingZone);
    }

    @Override
    public void deleteZone(Long id)  {

        zoneProvider.deleteZone(id);
    }

    @Override
    public List<ZoneDto> getAllZones() throws BusinessException {
        List<Zone> zones = zoneProvider.getAllZones();
        return zones.stream()
                .map(zoneMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ZoneDto getZoneById(Long id) throws BusinessException {
        Zone zone = zoneProvider.getZoneById(id);
        return zoneMapper.toDto(zone);
    }
}
