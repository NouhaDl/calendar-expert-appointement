package ma.autocash.booking.api.service;

import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.TechnicalException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AvailabilityService {

    AvailabilityDto saveAvailability(AvailabilityDto availabilityDto) throws TechnicalException;

    AvailabilityDto updateAvailability(Long id, AvailabilityDto availabilityDto) throws BusinessException, TechnicalException;

    void deleteAvailability(Long id) throws TechnicalException;

    void deleteAvailabilitiesByExpertAndDateAndTimeRange(Long expertId, LocalDate date, LocalTime startTime, LocalTime endTime) throws TechnicalException;

    AvailabilityDto getAvailabilityById(Long id) throws BusinessException, TechnicalException;

    List<AvailabilityDto> getAllAvailabilities() throws TechnicalException;


    List<AvailabilityDto> getAvailabilitiesByExpertAndTimeRange(Long expertId, LocalTime startTime, LocalTime endTime) throws TechnicalException;
}