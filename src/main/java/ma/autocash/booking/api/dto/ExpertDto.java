package ma.autocash.booking.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class ExpertDto {

  private Long id;

  @NotBlank(message = "First name must not be blank")
  private String firstName;

  @NotBlank(message = "Last name must not be blank")
  private String lastName;

  private List<ZoneDto> zones;

  private List<AvailabilityResponseDto> availabilities;

  private List<BookingResponseDto> bookings;

}
