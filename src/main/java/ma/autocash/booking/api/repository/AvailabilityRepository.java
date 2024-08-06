package ma.autocash.booking.api.repository;

import ma.autocash.booking.api.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    @Query("SELECT a FROM Availability a WHERE a.expert.id = :expertId AND a.date = :date")
    List<Availability> findAvailabilitiesByExpertAndDate(
            @Param("expertId") Long expertId, @Param("date") LocalDate date);

    @Modifying
    @Query("DELETE FROM Availability a WHERE a.expert.id = :expertId AND a.date = :date AND (a.startTime BETWEEN :startTimeStart AND :startTimeEnd)")
    void deleteAvailabilitiesByExpertAndDateAndTimeRange(
            @Param("expertId") Long expertId,
            @Param("date") LocalDate date,
            @Param("startTimeStart") LocalTime startTimeStart,
            @Param("startTimeEnd") LocalTime startTimeEnd);
}
