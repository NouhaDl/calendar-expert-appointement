package com.autocash.autocashapi.Services;
import com.autocash.autocashapi.Dtos.AvailabilityDto;
import com.autocash.autocashapi.Entities.AvailabilityEntity;
import com.autocash.autocashapi.Mappers.AvailabilityMapper;
import com.autocash.autocashapi.Repositories.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AvailabilityService {
    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private AvailabilityMapper availabilityMapper;

    public AvailabilityDto saveAvailability(AvailabilityDto availabilityDto) {
        AvailabilityEntity availabilityEntity = availabilityMapper.toEntity(availabilityDto);
        availabilityEntity = availabilityRepository.save(availabilityEntity);
        return availabilityMapper.toDto(availabilityEntity);
    }

    public List<AvailabilityDto> findAllAvailabilities() {
        List<AvailabilityEntity> availabilityEntities = availabilityRepository.findAll();
        return availabilityEntities.stream()
                .map(availabilityMapper::toDto)
                .collect(Collectors.toList());
    }

    public AvailabilityDto findAvailabilityById(Long id) {
        AvailabilityEntity availabilityEntity = availabilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Availability not found with id: " + id));
        return availabilityMapper.toDto(availabilityEntity);
    }

    public AvailabilityDto updateAvailability(Long id, AvailabilityDto availabilityDto) {
        AvailabilityEntity existingAvailabilityEntity = availabilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Availability not found with id: " + id));

        // Update existingAvailabilityEntity with new data from availabilityDto
        existingAvailabilityEntity.setStartTime(availabilityDto.getStartTime());
        existingAvailabilityEntity.setEndTime(availabilityDto.getEndTime());
        // Update other fields as needed

        AvailabilityEntity updatedAvailabilityEntity = availabilityRepository.save(existingAvailabilityEntity);
        return availabilityMapper.toDto(updatedAvailabilityEntity);
    }

    public boolean deleteAvailability(Long id) {
        if (availabilityRepository.existsById(id)) {
            availabilityRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Add other service methods as needed
}
