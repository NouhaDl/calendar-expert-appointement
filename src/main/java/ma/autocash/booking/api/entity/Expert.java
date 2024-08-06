package ma.autocash.booking.api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "experts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "expert", cascade = CascadeType.ALL)
    private List<Availability> availabilities;

    @ManyToMany
    @JoinTable(
            name = "experts_zones",
            joinColumns = @JoinColumn(name = "experts_id"),
            inverseJoinColumns = @JoinColumn(name = "zones_id"))
    private List<Zone> zones;

    @OneToMany(mappedBy = "expert", cascade = CascadeType.ALL)
    private List<Booking> bookings;
}
