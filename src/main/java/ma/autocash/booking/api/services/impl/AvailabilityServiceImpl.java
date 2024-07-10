package ma.autocash.booking.api.services.impl;

import lombok.SneakyThrows;
import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.exception.KeyValueErrorImpl;
import ma.autocash.booking.api.mapper.AvailabilityMapper;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.repository.AvailabilityRepository;
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

    private final AvailabilityRepository availabilityRepository;
    private final AvailabilityMapper availabilityMapper;

    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository, AvailabilityMapper availabilityMapper) {
        this.availabilityRepository = availabilityRepository;
        this.availabilityMapper = availabilityMapper;
    }

    @Override
    public AvailabilityDto saveAvailability(AvailabilityDto availabilityDto) throws TechnicalException {
        Objects.requireNonNull(availabilityDto, "AvailabilityDto must not be null");

        // Convert AvailabilityDto to Availability entity
        Availability availabilityEntity = availabilityMapper.toEntity(availabilityDto);

        // Save the entity and return the mapped DTO
        try {
            Availability savedAvailability = availabilityRepository.save(availabilityEntity);
            return availabilityMapper.toDto(savedAvailability);
        } catch (Exception e) {
            throw new TechnicalException("Error saving availability", e);
        }
    }

    @SneakyThrows
    @Override
    public AvailabilityDto updateAvailability(Long id, AvailabilityDto availabilityDto) throws BusinessException, TechnicalException {
        Objects.requireNonNull(id, "Availability ID must not be null");
        Objects.requireNonNull(availabilityDto, "AvailabilityDto must not be null");

        try {
            Availability existingAvailability = availabilityRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("availability.get.notfound", 404, 404)));

            existingAvailability.setExpertId(availabilityDto.getExpertId());
            existingAvailability.setDate(availabilityDto.getDate());
            existingAvailability.setStartTime(availabilityDto.getStartTime());
            existingAvailability.setEndTime(availabilityDto.getEndTime());

            Availability updatedAvailability = availabilityRepository.save(existingAvailability);
            return availabilityMapper.toDto(updatedAvailability);
        } catch (Exception e) {
            throw new TechnicalException("Error updating availability", e);
        }
    }

    @SneakyThrows
    @Override
    public void deleteAvailability(Long id) throws TechnicalException {
        Objects.requireNonNull(id, "Availability ID must not be null");
        try {
            availabilityRepository.deleteById(id);
        } catch (Exception e) {
            throw new TechnicalException("Error deleting availability", e);
        }
    }

    @SneakyThrows
    @Override
    public void deleteAvailabilitiesByExpertAndDateAndTimeRange(Long expertId, LocalDate date, LocalTime startTime, LocalTime endTime) throws TechnicalException {
        Objects.requireNonNull(expertId, "Expert ID must not be null");
        Objects.requireNonNull(date, "Date must not be null");
        Objects.requireNonNull(startTime, "Start time must not be null");
        Objects.requireNonNull(endTime, "End time must not be null");

        try {
            List<Availability> availabilitiesToDelete = availabilityRepository.findByExpert_IdAndDateAndStartTimeBetween(
                    expertId, date, startTime, endTime);
            availabilityRepository.deleteAll(availabilitiesToDelete);
        } catch (Exception e) {
            throw new TechnicalException("Error deleting availabilities by expert and date and time range", e);
        }
    }

    @SneakyThrows
    @Override
    public AvailabilityDto getAvailabilityById(Long id) throws BusinessException, TechnicalException {
        Objects.requireNonNull(id, "Availability ID must not be null");
        try {
            Availability availabilityEntity = availabilityRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("Availability.get.notfound", 404, 404)));
            return availabilityMapper.toDto(availabilityEntity);
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving availability by id", e);
        }
    }

    @SneakyThrows
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

    @SneakyThrows
    @Override
    public List<AvailabilityDto> getAvailabilitiesByExpertAndDateAndTimeRange(Long expertId, LocalDate date, LocalTime startTime, LocalTime endTime) throws TechnicalException {
        Objects.requireNonNull(expertId, "Expert ID must not be null");
        Objects.requireNonNull(date, "Date must not be null");
        Objects.requireNonNull(startTime, "Start time must not be null");
        Objects.requireNonNull(endTime, "End time must not be null");

        try {
            List<Availability> availabilities = availabilityRepository.findByExpert_IdAndDateAndStartTimeBetween(
                    expertId, date, startTime, endTime);
            return availabilities.stream()
                    .map(availabilityMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving availabilities by expert and date and time range", e);
        }
    }
}
