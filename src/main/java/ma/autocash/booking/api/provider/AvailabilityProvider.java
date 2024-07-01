package ma.autocash.booking.api.provider;


import ma.autocash.booking.api.entity.Availability;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AvailabilityProvider {
    Availability saveAvailability(Availability availability);

    Availability updateAvailability(Long id, Availability updatedAvailability);

    void deleteAvailability(Long id);

    List<Availability> getAllAvailabilities();

    Availability getAvailabilityById(Long id);

    List<Availability> getAvailabilitiesByExpertAndDateAndTimeRange(Long expertId, LocalDate date, LocalTime startTime, LocalTime endTime);
}

