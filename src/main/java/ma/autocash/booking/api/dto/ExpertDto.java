package ma.autocash.booking.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ExpertDto {
    private Long id;

    @NotNull(message = "First name must not be null")
    @Size(min = 1, message = "First name must not be empty")
    private String firstName;
    @NotNull(message = "Last name must not be null")
    @Size(min = 1, message = "Last name must not be empty")
    private String lastName;
    private List<Long> zoneIds;
    private List<Long> availabilityIds;
    private List<Long> bookingIds;

}
