package ma.autocash.booking.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "zones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "zone")
    private List<Booking> bookings;

    @OneToMany(mappedBy = "zone")
    private List<Availability> availabilities;

}
