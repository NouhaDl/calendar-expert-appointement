package ma.autocash.booking.api.provider.impl;

import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.exception.ApiErrors;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.provider.AvailabilityProvider;
import ma.autocash.booking.api.repository.AvailabilityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class AvailabilityProviderImpl implements AvailabilityProvider {

    private final AvailabilityRepository availabilityRepository;

    public AvailabilityProviderImpl(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public void addExpertAvailability( Availability availability) throws BusinessException {
        availabilityRepository.save(availability);
    }

    @Override
    public void updateAvailability( Availability availability) throws BusinessException {
        if (!availabilityRepository.existsById(availability.getId())) {
            throw new BusinessException(ApiErrors.AVAILABILITY_NOT_FOUND);
        }
        availabilityRepository.save(availability);

    }

    @Override
    public void deleteAvailability(Long id) throws BusinessException {
        if (!availabilityRepository.existsById(id)) {
            throw new BusinessException(ApiErrors.AVAILABILITY_NOT_FOUND);
        }
        availabilityRepository.deleteById(id);
    }

    @Override
    public Availability getAvailabilityById(Long id) throws BusinessException {
        return availabilityRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApiErrors.AVAILABILITY_NOT_FOUND));
    }

    @Override
    public List<Availability> getAllAvailabilities() throws BusinessException {
        List<Availability> availabilities = availabilityRepository.findAll();
        if (availabilities.isEmpty()) {
            throw new BusinessException(ApiErrors.AVAILABILITY_NOT_FOUND);
        }
        return availabilities;
    }

    @Override
    public List<Availability> getAvailabilitiesByExpertAndDate(Long expertId, LocalDate date) throws BusinessException {
        List<Availability> availabilities = availabilityRepository.findAvailabilitiesByExpertAndDate(expertId, date);
        if (availabilities.isEmpty()) {
            throw new BusinessException(ApiErrors.AVAILABILITY_NOT_FOUND);
        }
        return availabilities;
    }

    @Override
    public Availability createAvailabilityFromBooking(Booking booking) throws BusinessException {
        Availability availability = new Availability();
        availability.setExpert(booking.getExpert());
        availability.setDate(booking.getBookingDate());
        availability.setStartTime(booking.getStartTime());
        availability.setEndTime(booking.getEndTime());
        availability.setZone(booking.getZone());
        return availabilityRepository.save(availability);
    }
    @Override
    public void deleteAvailabilitiesByExpertAndDateAndTimeRange(Long expertId, LocalDate date, LocalTime startTimeStart, LocalTime startTimeEnd) throws BusinessException {
        availabilityRepository.deleteAvailabilitiesByExpertAndDateAndTimeRange(expertId, date, startTimeStart, startTimeEnd);
    }
}
