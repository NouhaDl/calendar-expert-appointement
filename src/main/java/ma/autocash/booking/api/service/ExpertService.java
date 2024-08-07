package ma.autocash.booking.api.service;

import jakarta.validation.Valid;
import ma.autocash.booking.api.dto.*;
import ma.autocash.booking.api.exception.BusinessException;

import java.util.List;

public interface ExpertService {

   void saveExpert(@Valid ExpertDto expertDto);

    void deleteExpert(Long id) throws BusinessException;

    void updateExpert(@Valid ExpertDto expertDto) throws BusinessException;

    ExpertDto getExpertById(Long id) throws BusinessException;

    List<ExpertDto> getAllExperts() throws BusinessException;

    void updateAvailabilityForExpert(Long expertId, AvailabilityResponseDto availabilityResponseDto) throws BusinessException;

    void updateBookingForExpert(Long expertId, BookingDto bookingDto) throws BusinessException;

    ExpertDto assignZonesToExpert(Long expertId, List<Long> zoneIds) throws BusinessException;
}
