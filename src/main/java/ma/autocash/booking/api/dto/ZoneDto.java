package ma.autocash.booking.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneDto {
    private Long id;
    private String name;
    @NotNull(message = "Expert ID must not be null")

    private List<Long> expertIds;
    private List<Long> bookingIds;

    // Getters and setters if Lombok doesn't work
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

    public List<Long> getExpertIds() {
        return expertIds;
    }

    public void setExpertIds(List<Long> expertIds) {
        this.expertIds = expertIds;
    }

    public List<Long> getBookingIds() {
        return bookingIds;
    }

    public void setBookingIds(List<Long> bookingIds) {
        this.bookingIds = bookingIds;
    }
}
