package ma.autocash.booking.api.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity
//@Table(name = "expert_details", schema = "expertises")
@Getter
@Setter
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
    @JsonManagedReference
    @OneToMany(mappedBy = "expert", cascade = CascadeType.ALL)
    private List<Availability> availabilities;
    @JsonManagedReference
    @ManyToMany
           @JoinTable(
            name = "expert_zone",
            joinColumns = @JoinColumn(name = "expert_id"),
            inverseJoinColumns = @JoinColumn(name = "zone_id"))
    private List<Zone> zones;
    @OneToMany(mappedBy = "expert", cascade = CascadeType.ALL)
    private List<Booking> bookings;

}

