package ma.autocash.booking.api.service;

import jakarta.validation.Valid;
import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.dto.AvailabilityResponseDto;
import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.exception.BusinessException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AvailabilityService {

    void addExpertAvailability(@Valid AvailabilityDto availabilityDto) throws BusinessException;

    void updateAvailability(@Valid AvailabilityDto availabilityDto) throws BusinessException;

    void deleteAvailability(Long id) throws BusinessException;

    AvailabilityResponseDto getAvailabilityById(Long id) throws BusinessException;

    List<AvailabilityResponseDto> getAllAvailabilities() throws BusinessException;

    List<AvailabilityResponseDto> getAvailabilitiesByExpertAndDate(Long expertId, LocalDate date) throws BusinessException;

    AvailabilityDto createAvailabilityFromBooking(@Valid BookingDto bookingDto) throws BusinessException;

    void deleteAvailabilitiesByExpertAndDateAndTimeRange(Long expertId, LocalDate date, LocalTime startTimeStart, LocalTime startTimeEnd) throws BusinessException;
}
