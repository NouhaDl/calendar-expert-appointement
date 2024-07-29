package ma.autocash.booking.api.service;

import jakarta.validation.Valid;
import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.exception.BusinessException;


import java.util.List;

public interface AvailabilityService {

    void addExpertAvailability(@Valid AvailabilityDto availabilityDto) throws  BusinessException;

    void updateAvailability( @Valid AvailabilityDto availabilityDto) throws  BusinessException;

    void deleteAvailability(Long id) ;

    AvailabilityDto getAvailabilityById(Long id) throws  BusinessException;

    List<AvailabilityDto> getAllAvailabilities() throws  BusinessException;
}
