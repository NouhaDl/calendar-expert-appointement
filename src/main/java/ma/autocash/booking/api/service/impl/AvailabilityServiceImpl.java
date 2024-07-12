package ma.autocash.booking.api.service.impl;

import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.KeyValueErrorImpl;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.mapper.AvailabilityMapper;
import ma.autocash.booking.api.repository.AvailabilityRepository;
import ma.autocash.booking.api.service.AvailabilityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = {TechnicalException.class, BusinessException.class})
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final AvailabilityMapper availabilityMapper;

    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository, AvailabilityMapper availabilityMapper) {
        this.availabilityRepository = availabilityRepository;
        this.availabilityMapper = availabilityMapper;
    }

    @Override
    public AvailabilityDto saveAvailability(AvailabilityDto availabilityDto) throws TechnicalException {
        Objects.requireNonNull(availabilityDto, "AvailabilityDto must not be null");

        try {
            Availability availabilityEntity = availabilityMapper.toEntity(availabilityDto);
            Availability savedAvailability = availabilityRepository.save(availabilityEntity);
            return availabilityMapper.toDto(savedAvailability);
        } catch (Exception e) {
            throw new TechnicalException("Error saving availability", e);
        }
    }

    @Override
    public AvailabilityDto updateAvailability(Long id, AvailabilityDto availabilityDto) throws TechnicalException {
        Objects.requireNonNull(id, "Availability ID must not be null");
        Objects.requireNonNull(availabilityDto, "AvailabilityDto must not be null");

        try {

            Availability existingAvailability = availabilityRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("availability.get.notfound", 404, 404)));


            if (availabilityDto.getExpertId() != null) {
                existingAvailability.setExpertId(availabilityDto.getExpertId());
            }
            if (availabilityDto.getDate() != null) {
                existingAvailability.setDate(availabilityDto.getDate());
            }
            if (availabilityDto.getStartTime() != null) {
                existingAvailability.setStartTime(availabilityDto.getStartTime());
            }
            if (availabilityDto.getEndTime() != null) {
                existingAvailability.setEndTime(availabilityDto.getEndTime());
            }

            Availability updatedAvailability = availabilityRepository.save(existingAvailability);


            return availabilityMapper.toDto(updatedAvailability);
        } catch (Exception e) {
            throw new TechnicalException("Error updating availability", e);
        }
    }

    @Override
    public void deleteAvailability(Long id) throws TechnicalException {
        Objects.requireNonNull(id, "Availability ID must not be null");
        try {
            availabilityRepository.deleteById(id);
        } catch (Exception e) {
            throw new TechnicalException("Error deleting availability", e);
        }
    }

    @Override
    public AvailabilityDto getAvailabilityById(Long id) throws TechnicalException {
        Objects.requireNonNull(id, "Availability ID must not be null");
        try {
            Availability availabilityEntity = availabilityRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("Availability.get.notfound", 404, 404)));
            return availabilityMapper.toDto(availabilityEntity);
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving availability by id", e);
        }
    }

    @Override
    public List<AvailabilityDto> getAllAvailabilities() throws TechnicalException {
        try {
            List<Availability> availabilities = availabilityRepository.findAll();
            return availabilities.stream()
                    .map(availabilityMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving all availabilities", e);
        }
    }

    @Override
    public List<AvailabilityDto> getAvailabilitiesByExpertAndTimeRange(Long expertId, LocalTime startTime, LocalTime endTime) throws TechnicalException {
        Objects.requireNonNull(expertId, "Expert ID must not be null");

        try {
            List<Availability> availabilities;

            if (startTime != null && endTime != null) {
                availabilities = availabilityRepository.findByExpert_IdAndStartTimeBetween(
                        expertId, startTime, endTime);
            } else {
                availabilities = availabilityRepository.findByExpert_Id(expertId);
            }

            return availabilities.stream()
                    .map(availabilityMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving availabilities by expert and time range", e);
        }
    }

    @Override
    public void deleteAvailabilitiesByExpertAndDateAndTimeRange(Long expertId, LocalDate date, LocalTime startTime, LocalTime endTime) throws TechnicalException {
        Objects.requireNonNull(expertId, "Expert ID must not be null");
        Objects.requireNonNull(date, "Date must not be null");

        try {
            List<Availability> availabilitiesToDelete = availabilityRepository.findByExpert_IdAndStartTimeBetween(
                    expertId, startTime, endTime);

            availabilityRepository.deleteAll(availabilitiesToDelete);
        } catch (Exception e) {
            throw new TechnicalException("Error deleting availabilities by expert and date/time range", e);
        }
    }
}
