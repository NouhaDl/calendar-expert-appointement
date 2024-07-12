package ma.autocash.booking.api.repository;

import ma.autocash.booking.api.entity.Availability;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import java.time.LocalTime;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByExpert_Id(Long expertId);
    List<Availability> findByExpert_IdAndStartTimeBetween(Long expertId, LocalTime startTime, LocalTime endTime);
}