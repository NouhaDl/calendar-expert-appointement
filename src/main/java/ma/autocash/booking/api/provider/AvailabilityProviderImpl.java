package ma.autocash.booking.api.provider;

import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.repository.AvailabilityRepository;
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
    public Availability saveAvailability(Availability availability) {
        try {
            return availabilityRepository.save(availability);
        } catch (Exception e) {
            throw new TechnicalException("Error saving availability", e);
        }
    }

    @Override
    public Availability updateAvailability(Availability availability) {
        try {
            if (availabilityRepository.existsById(availability.getId())) {
                return availabilityRepository.save(availability);
            } else {
                throw new BusinessException("Availability not found for update");
            }
        } catch (Exception e) {
            throw new TechnicalException("Error updating availability", e);
        }
    }

    @Override
    public void deleteAvailability(Long id) {
        try {
            if (availabilityRepository.existsById(id)) {
                availabilityRepository.deleteById(id);
            } else {
                throw new BusinessException("Availability not found for deletion");
            }
        } catch (Exception e) {
            throw new TechnicalException("Error deleting availability", e);
        }
    }

    @Override
    public List<Availability> getAllAvailabilities() {
        try {
            return availabilityRepository.findAll();
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving availabilities", e);
        }
    }

    @Override
    public Availability getAvailabilityById(Long id) {
        try {
            return availabilityRepository.findById(id).orElseThrow(() -> new BusinessException("Availability not found"));
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving availability by id", e);
        }
    }

    @Override
    public List<Availability> getAvailabilitiesByExpertAndDateAndTimeRange(Expert expert, LocalDate date, LocalTime startTime, LocalTime endTime) {
        try {
            return availabilityRepository.findByExpertAndDateAndTimeRange(expert, date, startTime, endTime);
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving availabilities by expert and date/time range", e);
        }
    }
}
