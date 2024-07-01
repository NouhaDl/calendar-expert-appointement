package ma.autocash.booking.api.repository;
import ma.autocash.booking.api.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
    public interface ExpertRepository extends JpaRepository<Expert, Long> {
    }
