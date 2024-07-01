package ma.autocash.booking.api.services;

import ma.autocash.booking.api.dto.AvailabilityDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AvailabilityService {

    AvailabilityDto saveAvailability(AvailabilityDto availabilityDto);

    AvailabilityDto updateAvailability(Long id, AvailabilityDto availabilityDto);

    void deleteAvailability(Long id);

    AvailabilityDto getAvailabilityById(Long id);

    List<AvailabilityDto> getAllAvailabilities();

    List<AvailabilityDto> getAvailabilitiesByExpertAndDateAndTimeRange(Long expertId, LocalDate date, LocalTime startTime, LocalTime endTime);
}
