package ma.autocash.booking.api.entity;

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
 // Getters and Setters
 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public String getName() {
  return name;
 }

 public void setName(String name) {
  this.name = name;
 }

 public List<Expert> getExperts() {
  return experts;
 }

 public void setExperts(List<Expert> experts) {
  this.experts = experts;
 }

 public List<Booking> getBookings() {
  return bookings;
 }

 public void setBookings(List<Booking> bookings) {
  this.bookings = bookings;
 }
}
