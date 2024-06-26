package com.AutocashApplication.rdv_expert.service;

import com.AutocashApplication.rdv_expert.entity.Availability;
import com.AutocashApplication.rdv_expert.entity.Expert;
import com.AutocashApplication.rdv_expert.repository.AvailabilityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;

    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public Availability saveAvailability(Availability availability) {
        return availabilityRepository.save(availability);
    }

    @Override
    public Availability updateAvailability(Availability availability) {
        return availabilityRepository.save(availability);
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
        return availabilityRepository.findById(id).orElse(null);
    }

    @Override
    public List<Availability> getAvailabilitiesByExpertAndDateAndTimeRange(Expert expert, LocalDate date, LocalTime startTime, LocalTime endTime) {
        return availabilityRepository.findByExpertAndDateAndTimeRange(expert, date, startTime, endTime);
    }
}
