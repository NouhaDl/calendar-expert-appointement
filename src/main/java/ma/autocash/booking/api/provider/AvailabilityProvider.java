package ma.autocash.booking.api.provider;

import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.TechnicalException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AvailabilityProvider {
    Availability saveAvailability(Availability availability) throws TechnicalException;
    // TODO: Remove id
    Availability updateAvailability(Long id, Availability updatedAvailability) throws BusinessException, TechnicalException;
    void deleteAvailability(Long id) throws TechnicalException;
    List<Availability> getAllAvailabilities() throws TechnicalException;
    Availability getAvailabilityById(Long id) throws BusinessException, TechnicalException;
    List<Availability> getAvailabilitiesByExpertAndDateAndTimeRange(Long expertId, LocalDate date, LocalTime startTime, LocalTime endTime) throws TechnicalException;
}

