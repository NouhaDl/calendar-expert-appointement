package ma.autocash.booking.api.provider;

import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.exception.BusinessException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AvailabilityProvider {

    void addExpertAvailability(Availability availability, Long expertId) throws BusinessException;

    void updateAvailability(Availability availability) throws BusinessException;

    void deleteAvailability(Long id) ;

    Availability getAvailabilityById(Long id) throws BusinessException;

    List<Availability> getAllAvailabilities() throws BusinessException;
    void deleteAvailabilitiesByExpertAndDateAndTimeRange(Long expertId, LocalDate date, LocalTime startTime, LocalTime endTime) throws BusinessException;
}

