package com.AutocashApplication.rdv_expert.repository;

import com.AutocashApplication.rdv_expert.entity.Availability;
import com.AutocashApplication.rdv_expert.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {



    List<Availability> findByExpertAndDateAndTimeRange(Expert expert, LocalDate date, LocalTime startTime, LocalTime endTime);
}
