package com.autocash.autocashapi.Services;
import com.autocash.autocashapi.Dtos.ZoneDto;
import com.autocash.autocashapi.Entities.ZoneEntity;
import com.autocash.autocashapi.Mappers.ZoneMapper;
import com.autocash.autocashapi.Repositories.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ZoneService {
    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private ZoneMapper zoneMapper;

    public ZoneDto saveZone(ZoneDto zoneDto) {
        ZoneEntity zoneEntity = zoneMapper.toEntity(zoneDto);
        zoneEntity = zoneRepository.save(zoneEntity);
        return zoneMapper.toDto(zoneEntity);
    }

    public List<ZoneDto> findAllZones() {
        List<ZoneEntity> zoneEntities = zoneRepository.findAll();
        return zoneEntities.stream()
                .map(zoneMapper::toDto)
                .collect(Collectors.toList());
    }

    public ZoneDto findZoneById(Long id) {
        ZoneEntity zoneEntity = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found with id: " + id));
        return zoneMapper.toDto(zoneEntity);
    }

    public ZoneDto updateZone(Long id, ZoneDto zoneDto) {
        ZoneEntity existingZoneEntity = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found with id: " + id));

        // Update existingZoneEntity with new data from zoneDto
        existingZoneEntity.setName(zoneDto.getName());

        ZoneEntity updatedZoneEntity = zoneRepository.save(existingZoneEntity);
        return zoneMapper.toDto(updatedZoneEntity);
    }

    public boolean deleteZone(Long id) {
        if (zoneRepository.existsById(id)) {
            zoneRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Add other service methods as needed
}