package ma.autocash.booking.api.provider.impl;

import ma.autocash.booking.api.entity.Availability;
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
    public void addExpertAvailability(Availability availability, Long expertId)  {
        availabilityRepository.save(availability);
    }

    @Override
    public void updateAvailability(Availability availability) throws BusinessException {
        Availability existingAvailability = availabilityRepository.findById(availability.getId())
                .orElseThrow(() -> new BusinessException(ApiErrors.AVAILABILITY_NOT_FOUND));
        existingAvailability.setExpert(availability.getExpert());
        existingAvailability.setDate(availability.getDate());
        existingAvailability.setStartTime(availability.getStartTime());
        existingAvailability.setEndTime(availability.getEndTime());

        availabilityRepository.save(existingAvailability);
    }


    @Override
    public void deleteAvailability(Long id) {

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
    public void deleteAvailabilitiesByExpertAndDateAndTimeRange(Long expertId, LocalDate date, LocalTime startTime, LocalTime endTime)  {
            availabilityRepository.deleteByExpertIdAndDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(expertId, date, startTime, endTime);

    }
}
