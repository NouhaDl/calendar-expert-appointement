package ma.autocash.booking.api.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity
@Getter
@Setter
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
 public Zone() {
 }
 public Zone(Long id) {
  this.id = id;
 }
 @Override
 public String toString() {
  return "Zone{" +
          "id=" + id +
          ", name='" + name + '\'' +
          '}';
 }


}
