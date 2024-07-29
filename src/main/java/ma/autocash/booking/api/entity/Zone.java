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

	private String name;


	@ManyToMany(mappedBy = "zones")
	private List<Expert> experts;


	@OneToMany(mappedBy = "zone", cascade = CascadeType.ALL)
	private List<Booking> bookings;


}
