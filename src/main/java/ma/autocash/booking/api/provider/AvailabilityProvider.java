package ma.autocash.booking.api.provider;

import jakarta.validation.Valid;
import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.exception.BusinessException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AvailabilityProvider {

    void addExpertAvailability(@Valid Availability availability) throws BusinessException;

    void updateAvailability(@Valid Availability availability) throws BusinessException;

    void deleteAvailability(Long id) throws BusinessException;

    Availability getAvailabilityById(Long id) throws BusinessException;

    List<Availability> getAllAvailabilities() throws BusinessException;

    List<Availability> getAvailabilitiesByExpertAndDate(Long expertId, LocalDate date) throws BusinessException;

    Availability createAvailabilityFromBooking(Booking booking) throws BusinessException;

    void deleteAvailabilitiesByExpertAndDateAndTimeRange(Long expertId, LocalDate date, LocalTime startTimeStart, LocalTime startTimeEnd) throws BusinessException;
}
