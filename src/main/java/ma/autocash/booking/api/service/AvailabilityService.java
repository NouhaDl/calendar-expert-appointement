package ma.autocash.booking.api.service;
import jakarta.validation.Valid;
import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.TechnicalException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
public interface AvailabilityService {
    AvailabilityDto saveAvailability(@Valid AvailabilityDto availabilityDto) throws TechnicalException;
    AvailabilityDto updateAvailability(Long id, @Valid AvailabilityDto availabilityDto) throws TechnicalException, BusinessException;
    void deleteAvailability(Long id) throws TechnicalException;
    AvailabilityDto getAvailabilityById(Long id) throws TechnicalException, BusinessException;
    List<AvailabilityDto> getAllAvailabilities() throws TechnicalException;
    List<AvailabilityDto> getAvailabilitiesByExpertAndTimeRange(Long expertId, LocalTime startTime, LocalTime endTime) throws TechnicalException;
    void deleteAvailabilitiesByExpertAndDateAndTimeRange(Long expertId, LocalDate date, LocalTime startTime, LocalTime endTime) throws TechnicalException;
    List<AvailabilityDto> getAvailableAvailabilitiesForExpert(Long expertId) throws TechnicalException;
}
