package ma.autocash.booking.api.repository;
import ma.autocash.booking.api.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByAvailability_Id(Long availabilityId);

    List<Booking> findByExpert_Id(Long expertId);
}
