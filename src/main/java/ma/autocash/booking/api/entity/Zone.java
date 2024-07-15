package ma.autocash.booking.api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import lombok.*;

import java.util.List;

@Entity
//@Table(name = "zone_details", schema = "expertises")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Zone {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private String name;

 @JsonManagedReference
 @ManyToMany(mappedBy = "zones")
 private List<Expert> experts;

 @JsonManagedReference
 @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL)
 private List<Booking> bookings;

}
