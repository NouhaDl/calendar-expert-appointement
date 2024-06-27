package ma.autocash.booking.api.service;

import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.provider.AvailabilityProvider;
import ma.autocash.booking.api.mapper.AvailabilityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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
        Availability availability = availabilityMapper.toEntity(availabilityDto, new Expert()); // Replace with actual Expert
        Availability savedAvailability = availabilityProvider.saveAvailability(availability);
        return availabilityMapper.toDTO(savedAvailability);
    }

    @Override
    public AvailabilityDto updateAvailability(Long id, AvailabilityDto availabilityDto) {
        Availability existingAvailability = availabilityProvider.getAvailabilityById(id);
        if (existingAvailability != null) {
            Expert expert = new Expert(); // Replace with actual Expert
            Availability updatedAvailability = availabilityMapper.toEntity(availabilityDto, expert);
            updatedAvailability.setId(id);
            Availability savedAvailability = availabilityProvider.updateAvailability(updatedAvailability);
            return availabilityMapper.toDTO(savedAvailability);
        }
        return null; // Handle scenario where availability with given ID is not found
    }

    @Override
    public void deleteAvailability(Long id) {
        availabilityProvider.deleteAvailability(id);
    }

    @Override
    public AvailabilityDto getAvailabilityById(Long id) {
        Availability availability = availabilityProvider.getAvailabilityById(id);
        return availabilityMapper.toDTO(availability);
    }

    @Override
    public List<AvailabilityDto> getAllAvailabilities() {
        List<Availability> availabilities = availabilityProvider.getAllAvailabilities();
        return availabilities.stream()
                .map(availabilityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AvailabilityDto> getAvailabilitiesByExpertAndDateAndTimeRange(Long expertId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        Expert expert = new Expert();
        expert.setId(expertId);
        List<Availability> availabilities = availabilityProvider.getAvailabilitiesByExpertAndDateAndTimeRange(expert, date, startTime, endTime);
        return availabilities.stream()
                .map(availabilityMapper::toDTO)
                .collect(Collectors.toList());
    }
}
