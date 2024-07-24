package ma.autocash.booking.api.provider.impl;

import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.exception.KeyValueErrorImpl;
import ma.autocash.booking.api.repository.AvailabilityRepository;
import ma.autocash.booking.api.provider.AvailabilityProvider;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.TechnicalException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AvailabilityProviderImpl implements AvailabilityProvider {
    private final AvailabilityRepository availabilityRepository;

    public AvailabilityProviderImpl(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public Availability saveAvailability(Availability availability, Long expertId) throws TechnicalException {
        var expert = expertProvider.getExpertById(expertId);
        availability.setExpert(expert);
        //TODO: Add functional logic here if needed
        availabilityRepository.saveAvailability(availability);
    }

    @Override
    public Availability updateAvailability(Long id, Availability updatedAvailability) throws BusinessException {
        // TODO: CENTRALIZE ALL ERRORS
        // Create a file with all ApiErrors as constants
        // Example: AVAILABILITY_NOT_FOUND("Availability.get.notfound", 404, 404))
        // WE CAN USE THIS WITH => ApiErrors.AVAILABILITY_NOT_FOUND
        Availability existingAvailability = availabilityRepository.findById(id)
                .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("Availability.get.notfound", 404, 404)));
        existingAvailability.setDate(updatedAvailability.getDate());
        existingAvailability.setStartTime(updatedAvailability.getStartTime());
        existingAvailability.setEndTime(updatedAvailability.getEndTime());
        return availabilityRepository.save(existingAvailability);
    }

    @Override
    public void deleteAvailability(Long id) throws TechnicalException {
        availabilityRepository.deleteById(id);
        // TODO: Please check all exceptions and only keep necessary ones
//        try {
//            availabilityRepository.deleteById(id);
//        } catch (Exception e) {
//            throw new TechnicalException("Failed to delete availability with id: " + id, e);
//        }
    }

    @Override
    public List<Availability> getAllAvailabilities() throws TechnicalException {
        // TODO: Please check all exceptions and only keep necessary ones
        try {
            return availabilityRepository.findAll();
        } catch (Exception e) {
            throw new TechnicalException("Failed to retrieve all availabilities", e);
        }
    }

    @Override
    public Availability getAvailabilityById(Long id) throws TechnicalException {
        // TODO: Please check all exceptions and only keep necessary ones
        try {
            return availabilityRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("Availability.get.notfound", 404, 404)));
        } catch (Exception e) {
            throw new TechnicalException("Failed to retrieve availability with id: " + id, e);
        }
    }

    @Override
    public List<Availability> getAvailabilitiesByExpertAndDateAndTimeRange(Long expertId, LocalDate date, LocalTime startTime, LocalTime endTime) throws TechnicalException {
        // TODO: Please check all exceptions and only keep necessary ones
        try {
            return availabilityRepository.findByExpert_IdAndStartTimeBetween(expertId, startTime, endTime);
        } catch (Exception e) {
            throw new TechnicalException("Failed to retrieve availabilities by expert and time range", e);
        }
    }
}
