package ma.autocash.booking.api.repository;

import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {



    List<Availability> findByExpertAndDateAndTimeRange(Expert expert, LocalDate date, LocalTime startTime, LocalTime endTime);
}
