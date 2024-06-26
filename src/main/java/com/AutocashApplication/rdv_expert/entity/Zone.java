package com.AutocashApplication.rdv_expert.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
