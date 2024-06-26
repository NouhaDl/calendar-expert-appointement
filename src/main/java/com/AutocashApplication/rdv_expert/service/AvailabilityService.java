package com.AutocashApplication.rdv_expert.service;

import com.AutocashApplication.rdv_expert.entity.Availability;
import com.AutocashApplication.rdv_expert.entity.Expert;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AvailabilityService {
    Availability saveAvailability(Availability availability);
    Availability updateAvailability(Availability availability);
    void deleteAvailability(Long id);
    List<Availability> getAllAvailabilities();
    Availability getAvailabilityById(Long id);
    List<Availability> getAvailabilitiesByExpertAndDateAndTimeRange(Expert expert, LocalDate date, LocalTime startTime, LocalTime endTime);
}
