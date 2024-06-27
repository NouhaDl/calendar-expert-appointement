package ma.autocash.booking.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExpertDto {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Long> zoneIds;
    private List<Long> availabilityIds;
    private List<Long> bookingIds;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Long> getZoneIds() {
        return zoneIds;
    }

    public void setZoneIds(List<Long> zoneIds) {
        this.zoneIds = zoneIds;
    }

    public List<Long> getAvailabilityIds() {
        return availabilityIds;
    }

    public void setAvailabilityIds(List<Long> availabilityIds) {
        this.availabilityIds = availabilityIds;
    }

    public List<Long> getBookingIds() {
        return bookingIds;
    }

    public void setBookingIds(List<Long> bookingIds) {
        this.bookingIds = bookingIds;
    }
}
