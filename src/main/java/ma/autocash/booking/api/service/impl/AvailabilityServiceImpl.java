package ma.autocash.booking.api.service.impl;
import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.KeyValueErrorImpl;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.mapper.AvailabilityMapper;
import ma.autocash.booking.api.repository.AvailabilityRepository;
import ma.autocash.booking.api.service.AvailabilityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
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
    public AvailabilityDto saveAvailability(@Valid AvailabilityDto availabilityDto) throws TechnicalException {
        validateAvailabilityDto(availabilityDto);
        try {
            Availability availabilityEntity = availabilityMapper.toEntity(availabilityDto);
            setExpertToAvailabilityEntity(availabilityDto, availabilityEntity);
            // Check if the availability already exists
            if (!availabilityRepository.existsByExpertIdAndDateAndStartTimeAndEndTime(
                    availabilityDto.getExpertId(), availabilityDto.getDate(),
                    availabilityDto.getStartTime(), availabilityDto.getEndTime())) {
                Availability savedAvailability = availabilityRepository.save(availabilityEntity);
                return availabilityMapper.toDto(savedAvailability);
            }
            // If it already exists
            return null;
        } catch (Exception e) {
            throw new TechnicalException("Error saving availability", e);
        }
    }
    @Override
    public AvailabilityDto updateAvailability(Long id, @Valid AvailabilityDto availabilityDto) throws TechnicalException, BusinessException {
        validateId(id);
        validateAvailabilityDto(availabilityDto);
        try {
            Availability existingAvailability = availabilityRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("availability.get.notfound", 404, 404)));
            updateAvailabilityEntity(existingAvailability, availabilityDto);
            Availability updatedAvailability = availabilityRepository.save(existingAvailability);
            return availabilityMapper.toDto(updatedAvailability);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException("Error updating availability", e);
        }
    }
    @Override
    public void deleteAvailability(Long id) throws TechnicalException {
        validateId(id);
        try {
            availabilityRepository.deleteById(id);
        } catch (Exception e) {
            throw new TechnicalException("Error deleting availability", e);
        }
    }
    @Override
    public AvailabilityDto getAvailabilityById(Long id) throws TechnicalException, BusinessException {
        validateId(id);
        try {
            Availability availabilityEntity = availabilityRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("availability.get.notfound", 404, 404)));
            return availabilityMapper.toDto(availabilityEntity);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving availability by id", e);
        }
    }
    @Override
    public List<AvailabilityDto> getAllAvailabilities() throws TechnicalException {
        try {
            return availabilityRepository.findAll().stream()
                    .map(availabilityMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving all availabilities", e);
        }
    }
    @Override
    public List<AvailabilityDto> getAvailabilitiesByExpertAndTimeRange(Long expertId, LocalTime startTime, LocalTime endTime) throws TechnicalException {
        validateId(expertId);
        try {
            List<Availability> availabilities;

            if (startTime != null && endTime != null) {
                availabilities = availabilityRepository.findByExpert_IdAndStartTimeBetween(expertId, startTime, endTime);
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
        validateId(expertId);
        validateDate(date);

        try {
            List<Availability> availabilitiesToDelete = availabilityRepository.findByExpert_IdAndStartTimeBetween(expertId, startTime, endTime);
            availabilityRepository.deleteAll(availabilitiesToDelete);
        } catch (Exception e) {
            throw new TechnicalException("Error deleting availabilities by expert and date/time range", e);
        }
    }
    @Override
    public List<AvailabilityDto> getAvailableAvailabilitiesForExpert(Long expertId) throws TechnicalException {
        validateId(expertId);
        try {
            List<Availability> availabilities = availabilityRepository.findByExpert_Id(expertId);
            return availabilities.stream()
                    .map(availabilityMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving available availabilities for expert", e);
        }
    }
    private void validateId(Long id) {
        Objects.requireNonNull(id, "ID must not be null");
    }
    private void validateDate(LocalDate date) {
        Objects.requireNonNull(date, "Date must not be null");
    }
    private void validateAvailabilityDto(AvailabilityDto availabilityDto) {
        Objects.requireNonNull(availabilityDto, "AvailabilityDto must not be null");
    }
    private void setExpertToAvailabilityEntity(AvailabilityDto availabilityDto, Availability availabilityEntity) {
        if (availabilityDto.getExpertId() != null) {
            Expert expert = new Expert();
            expert.setId(availabilityDto.getExpertId());
            availabilityEntity.setExpert(expert);
        }
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
