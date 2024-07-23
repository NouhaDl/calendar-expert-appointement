package ma.autocash.booking.api.repository;
import ma.autocash.booking.api.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}