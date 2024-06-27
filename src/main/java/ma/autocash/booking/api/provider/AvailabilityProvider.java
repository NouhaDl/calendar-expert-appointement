package ma.autocash.booking.api.provider;

import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.entity.Expert;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AvailabilityProvider {
    Availability saveAvailability(Availability availability);
    Availability updateAvailability(Availability availability);
    void deleteAvailability(Long id);
    List<Availability> getAllAvailabilities();
    Availability getAvailabilityById(Long id);
    List<Availability> getAvailabilitiesByExpertAndDateAndTimeRange(Expert expert, LocalDate date, LocalTime startTime, LocalTime endTime);
}
