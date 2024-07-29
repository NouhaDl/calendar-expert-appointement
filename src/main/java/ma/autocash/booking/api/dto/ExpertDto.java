package ma.autocash.booking.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;
@Data
public class ExpertDto {

    private Long id;

  @NotBlank(message="firstname must not be blank")
    private String firstName;

    @NotBlank(message="Lastname must not be blank")
    private String lastName;

    private List<Long> zoneIds;

    private List<Long> availabilityIds;

    private List<Long> bookingIds;

}
