package ma.autocash.booking.api.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
@Entity
@Table(name = "booking")
@Getter
@Setter
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expert_id", referencedColumnName = "id")
    private Expert expert;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id", referencedColumnName = "id")
    private Zone zone;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "availability_id", referencedColumnName = "id")
    private Availability availability;
    @Column(name = "booking_date")
    private LocalDate bookingDate;
    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "end_time")
    private LocalTime endTime;

    public Booking(Long id) {
        this.id = id;
    }
    public Booking() {
    }
}

