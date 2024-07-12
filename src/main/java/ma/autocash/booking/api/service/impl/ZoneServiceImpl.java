package ma.autocash.booking.api.services.impl;

import lombok.SneakyThrows;
import ma.autocash.booking.api.dto.ZoneDto;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.KeyValueErrorImpl;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.mapper.ZoneMapper;
import ma.autocash.booking.api.repository.ZoneRepository;
import ma.autocash.booking.api.services.ZoneService;
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
            Objects.requireNonNull(zoneDto, "ZoneDto must not be null");
            Objects.requireNonNull(zoneDto.getName(), "Zone name must not be null");

            Zone zoneEntity = zoneMapper.toEntity(zoneDto);
            Zone savedZone = zoneRepository.save(zoneEntity);

            ZoneDto savedZoneDto = zoneMapper.toDto(savedZone);
            savedZoneDto.setId(savedZone.getId());

            return savedZoneDto;
        } catch (Exception e) {
            throw new TechnicalException("Error saving zone", e);
        }
    }

    @Override
    public ZoneDto updateZone(Long id, ZoneDto zoneDto) throws TechnicalException {
        try {
            Zone existingZone = zoneRepository.findById(id)
                    .orElseThrow(() -> new TechnicalException(new KeyValueErrorImpl("zone.update.notfound", 404, 404)));

            existingZone.setName(zoneDto.getName());

            zoneRepository.save(existingZone);

            return zoneMapper.toDto(existingZone);
        } catch (TechnicalException e) {
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
                throw new TechnicalException(new KeyValueErrorImpl("zone.delete.notfound", 404, 404));
            }
        } catch (TechnicalException e) {
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
                    .orElseThrow(() -> new TechnicalException(new KeyValueErrorImpl("zone.notfound", 404, 404)));
            return zoneMapper.toDto(zone);
        } catch (TechnicalException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving zone by id", e);
        }
    }
}
