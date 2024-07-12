package ma.autocash.booking.api.provider.impl;

import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.exception.KeyValueErrorImpl;
import ma.autocash.booking.api.mapper.AvailabilityMapper;
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
    private final AvailabilityMapper availabilityMapper;

    public AvailabilityProviderImpl(AvailabilityRepository availabilityRepository, AvailabilityMapper availabilityMapper) {
        this.availabilityRepository = availabilityRepository;
        this.availabilityMapper = availabilityMapper;
    }

    @Override
    public Availability saveAvailability(Availability availability) throws TechnicalException {
        try {
            return availabilityRepository.save(availability);
        } catch (Exception e) {
            throw new TechnicalException("Failed to save availability", e);
        }
    }

    @Override
    public Availability updateAvailability(Long id, Availability updatedAvailability) throws TechnicalException {
        try {
            Availability existingAvailability = availabilityRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("Availability.get.notfound", 404, 404)));

            existingAvailability.setDate(updatedAvailability.getDate());
            existingAvailability.setStartTime(updatedAvailability.getStartTime());
            existingAvailability.setEndTime(updatedAvailability.getEndTime());

            return availabilityRepository.save(existingAvailability);
        } catch (Exception e) {
            throw new TechnicalException("Failed to update availability with id: " + id, e);
        }
    }

    @Override
    public void deleteAvailability(Long id) throws TechnicalException {
        try {
            availabilityRepository.deleteById(id);
        } catch (Exception e) {
            throw new TechnicalException("Failed to delete availability with id: " + id, e);
        }
    }

    @Override
    public List<Availability> getAllAvailabilities() throws TechnicalException {
        try {
            return availabilityRepository.findAll();
        } catch (Exception e) {
            throw new TechnicalException("Failed to retrieve all availabilities", e);
        }
    }

    @Override
    public Availability getAvailabilityById(Long id) throws TechnicalException {
        try {
            return availabilityRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("Availability.get.notfound", 404, 404)));
        } catch (Exception e) {
            throw new TechnicalException("Failed to retrieve availability with id: " + id, e);
        }
    }

    @Override
    public List<Availability> getAvailabilitiesByExpertAndDateAndTimeRange(Long expertId, LocalDate date, LocalTime startTime, LocalTime endTime) throws TechnicalException {
        try {
            return availabilityRepository.findByExpert_IdAndStartTimeBetween(expertId, startTime, endTime);
        } catch (Exception e) {
            throw new TechnicalException("Failed to retrieve availabilities by expert and time range", e);
        }
    }
}
