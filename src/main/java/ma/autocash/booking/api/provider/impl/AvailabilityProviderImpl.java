package ma.autocash.booking.api.provider.impl;

import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.mapper.AvailabilityMapper;
import ma.autocash.booking.api.repository.AvailabilityRepository;
import ma.autocash.booking.api.provider.AvailabilityProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class AvailabilityProviderImpl implements AvailabilityProvider {

    private final AvailabilityRepository availabilityRepository;
    private final AvailabilityMapper availabilityMapper;

    public AvailabilityProviderImpl(AvailabilityRepository availabilityRepository, AvailabilityMapper availabilityMapper) {
        this.availabilityRepository = availabilityRepository;
        this.availabilityMapper = availabilityMapper;
    }

    @Override
    public Availability saveAvailability(Availability availability) {
        return availabilityRepository.save(availability);
    }

    @Override
    public Availability updateAvailability(Long id, Availability updatedAvailability) {
        Availability existingAvailability = availabilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Availability not found with id: " + id));

        // Update fields from updatedAvailability to existingAvailability
        existingAvailability.setDate(updatedAvailability.getDate());
        existingAvailability.setStartTime(updatedAvailability.getStartTime());
        existingAvailability.setEndTime(updatedAvailability.getEndTime());
        // Update other fields as needed

        return availabilityRepository.save(existingAvailability);
    }

    @Override
    public void deleteAvailability(Long id) {
        availabilityRepository.deleteById(id);
    }

    @Override
    public List<Availability> getAllAvailabilities() {
        return availabilityRepository.findAll();
    }

    @Override
    public Availability getAvailabilityById(Long id) {
        return availabilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Availability not found with id: " + id));
    }

    @Override
    public List<Availability> getAvailabilitiesByExpertAndDateAndTimeRange(Long expertId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        // Assuming you have an expert entity or object to pass to findByExpertAndDateAndStartTimeBetween
        Expert expert = new Expert(); // Replace with actual expert retrieval logic

        return availabilityRepository.findByExpertAndDateAndStartTimeBetween(expert, date, startTime, endTime);
    }
}
