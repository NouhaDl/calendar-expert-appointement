package ma.autocash.booking.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
//@Table(name = "booking_details", schema = "expertises")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "expert_id")
    private Expert expert;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;

    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;
}