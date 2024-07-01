package ma.autocash.booking.api.services.impl;

import ma.autocash.booking.api.dto.ZoneDto;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.mapper.ZoneMapper;
import ma.autocash.booking.api.repository.ZoneRepository;
import ma.autocash.booking.api.services.ZoneService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    private final ZoneMapper zoneMapper;

    public ZoneServiceImpl(ZoneRepository zoneRepository, ZoneMapper zoneMapper) {
        this.zoneRepository = zoneRepository;
        this.zoneMapper = zoneMapper;
    }

    @Override
    public ZoneDto saveZone(ZoneDto zoneDto) {
        try {
            Zone zone = zoneMapper.toEntity(zoneDto);
            Zone savedZone = zoneRepository.save(zone);
            return zoneMapper.toDto(savedZone);
        } catch (Exception e) {
            throw new TechnicalException("Error saving zone", e);
        }
    }

    @Override
    public ZoneDto updateZone(Long id, ZoneDto zoneDto) {
        try {
            Zone existingZone = zoneRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Zone not found for update"));

            existingZone.setName(zoneDto.getName()); // Update name if needed

            // Handle associations if necessary, like experts and bookings

            zoneRepository.save(existingZone);

            return zoneMapper.toDto(existingZone);
        } catch (Exception e) {
            throw new TechnicalException("Error updating zone", e);
        }
    }

    @Override
    public void deleteZone(Long id) {
        try {
            if (zoneRepository.existsById(id)) {
                zoneRepository.deleteById(id);
            } else {
                throw new BusinessException("Zone not found for deletion");
            }
        } catch (Exception e) {
            throw new TechnicalException("Error deleting zone", e);
        }
    }

    @Override
    public List<ZoneDto> getAllZones() {
        try {
            List<Zone> zones = zoneRepository.findAll();
            return zones.stream()
                    .map(zoneMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving zones", e);
        }
    }

    @Override
    public ZoneDto getZoneById(Long id) {
        try {
            Zone zone = zoneRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Zone not found"));
            return zoneMapper.toDto(zone);
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving zone by id", e);
        }
    }
}
