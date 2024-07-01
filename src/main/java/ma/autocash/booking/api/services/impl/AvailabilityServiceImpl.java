package ma.autocash.booking.api.services.impl;

import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.mapper.AvailabilityMapper;
import ma.autocash.booking.api.provider.AvailabilityProvider;
import ma.autocash.booking.api.services.AvailabilityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityProvider availabilityProvider;
    private final AvailabilityMapper availabilityMapper;

    public AvailabilityServiceImpl(AvailabilityProvider availabilityProvider, AvailabilityMapper availabilityMapper) {
        this.availabilityProvider = availabilityProvider;
        this.availabilityMapper = availabilityMapper;
    }

    @Override
    public AvailabilityDto saveAvailability(AvailabilityDto availabilityDto) {
        Objects.requireNonNull(availabilityDto, "AvailabilityDto must not be null");

        // Convert AvailabilityDto to Availability entity
        Availability availabilityEntity = availabilityMapper.toEntity(availabilityDto);

        // Save the entity and return the mapped DTO
        Availability savedAvailability = availabilityProvider.saveAvailability(availabilityEntity);
        return availabilityMapper.toDto(savedAvailability);
    }

    @Override
    public AvailabilityDto updateAvailability(Long id, AvailabilityDto availabilityDto) {
        Objects.requireNonNull(id, "Availability ID must not be null");
        Objects.requireNonNull(availabilityDto, "AvailabilityDto must not be null");

        // Convert DTO to entity
        Availability availabilityEntity = availabilityMapper.toEntity(availabilityDto);
        availabilityEntity.setId(id);

        // Update availability and return DTO
        Availability updatedAvailability = availabilityProvider.updateAvailability(id, availabilityEntity);
        return availabilityMapper.toDto(updatedAvailability);
    }

    @Override
    public void deleteAvailability(Long id) {
        Objects.requireNonNull(id, "Availability ID must not be null");
        availabilityProvider.deleteAvailability(id);
    }

    @Override
    public AvailabilityDto getAvailabilityById(Long id) {
        Objects.requireNonNull(id, "Availability ID must not be null");
        Availability availabilityEntity = availabilityProvider.getAvailabilityById(id);
        return availabilityMapper.toDto(availabilityEntity);
    }

    @Override
    public List<AvailabilityDto> getAllAvailabilities() {
        List<Availability> availabilities = availabilityProvider.getAllAvailabilities();
        return availabilities.stream()
                .map(availabilityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AvailabilityDto> getAvailabilitiesByExpertAndDateAndTimeRange(Long expertId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        Objects.requireNonNull(expertId, "Expert ID must not be null");
        Objects.requireNonNull(date, "Date must not be null");
        Objects.requireNonNull(startTime, "Start time must not be null");
        Objects.requireNonNull(endTime, "End time must not be null");

        List<Availability> availabilities = availabilityProvider.getAvailabilitiesByExpertAndDateAndTimeRange(expertId, date, startTime, endTime);
        return availabilities.stream()
                .map(availabilityMapper::toDto)
                .collect(Collectors.toList());
    }
}
