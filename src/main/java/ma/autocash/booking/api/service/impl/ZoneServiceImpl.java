package ma.autocash.booking.api.service.impl;
import ma.autocash.booking.api.dto.ZoneDto;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.EntityNotFoundException;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.mapper.ZoneMapper;
import ma.autocash.booking.api.repository.ZoneRepository;
import ma.autocash.booking.api.service.ZoneService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
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
    public ZoneDto saveZone(ZoneDto zoneDto) throws TechnicalException {
        try {
            validateZoneDto(zoneDto);
            Zone zoneEntity = zoneMapper.toEntity(zoneDto);
            Zone savedZone = zoneRepository.save(zoneEntity);
            return zoneMapper.toDto(savedZone);
        } catch (Exception e) {
            throw new TechnicalException("Error saving zone", e);
        }
    }
    @Override
    public ZoneDto updateZone(Long id, ZoneDto zoneDto) throws TechnicalException {
        try {
            Zone existingZone = zoneRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Zone", id));
            existingZone.setName(zoneDto.getName());
            zoneRepository.save(existingZone);
            return zoneMapper.toDto(existingZone);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException("Error updating zone", e);
        }
    }
    @Override
    public void deleteZone(Long id) throws TechnicalException {
        try {
            if (zoneRepository.existsById(id)) {
                zoneRepository.deleteById(id);
            } else {
                throw new EntityNotFoundException("Zone", id);
            }
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException("Error deleting zone", e);
        }
    }
    @Override
    public List<ZoneDto> getAllZones() throws TechnicalException {
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
    public ZoneDto getZoneById(Long id) throws TechnicalException {
        try {
            Zone zone = zoneRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Zone", id));
            return zoneMapper.toDto(zone);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving zone by id", e);
        }
    }
    private void validateZoneDto(ZoneDto zoneDto) {
        Objects.requireNonNull(zoneDto, "ZoneDto must not be null");
        Objects.requireNonNull(zoneDto.getName(), "Zone name must not be null");
    }
}
