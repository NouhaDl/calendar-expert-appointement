package ma.autocash.booking.api.service.impl;

import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.mapper.AvailabilityMapper;
import ma.autocash.booking.api.provider.AvailabilityProvider;
import ma.autocash.booking.api.service.AvailabilityService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;

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
    public void addExpertAvailability(@Valid AvailabilityDto availabilityDto) throws  BusinessException {
        Availability availability = availabilityMapper.toEntity(availabilityDto);
        Long expertId = availabilityDto.getExpertId();
        availabilityProvider.addExpertAvailability(availability, expertId);
    }

    @Override
    public void updateAvailability(@Valid AvailabilityDto availabilityDto) throws BusinessException {
        Availability existingAvailability = availabilityProvider.getAvailabilityById(availabilityDto.getId());
        updateAvailabilityEntity(existingAvailability, availabilityDto);
        availabilityProvider.updateAvailability(existingAvailability);
    }
    @Override
    public void deleteAvailability(Long id) {
        availabilityProvider.deleteAvailability(id);
    }

    @Override
    public AvailabilityDto getAvailabilityById(Long id) throws BusinessException{
        Availability availability = availabilityProvider.getAvailabilityById(id);
        return availabilityMapper.toDto(availability);
    }

    @Override
    public List<AvailabilityDto> getAllAvailabilities() throws  BusinessException {
        List<Availability> availabilities = availabilityProvider.getAllAvailabilities();
        return availabilities.stream()
                .map(availabilityMapper::toDto)
                .collect(Collectors.toList());
    }

    private void updateAvailabilityEntity(Availability availability, AvailabilityDto availabilityDto) {
        if (availabilityDto.getExpertId() != null) {
            Expert expert = new Expert();
            expert.setId(availabilityDto.getExpertId());
            availability.setExpert(expert);
        }
        if (availabilityDto.getDate() != null) {
            availability.setDate(availabilityDto.getDate());
        }
        if (availabilityDto.getStartTime() != null) {
            availability.setStartTime(availabilityDto.getStartTime());
        }
        if (availabilityDto.getEndTime() != null) {
            availability.setEndTime(availabilityDto.getEndTime());
        }
    }
}
