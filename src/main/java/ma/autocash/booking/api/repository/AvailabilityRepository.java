package ma.autocash.booking.api.repository;

import ma.autocash.booking.api.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    void deleteByExpertIdAndDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(Long expertId, LocalDate date, LocalTime startTime, LocalTime endTime);


}