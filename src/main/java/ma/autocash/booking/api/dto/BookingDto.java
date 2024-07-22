package ma.autocash.booking.api.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingDto {
    private Long id;
    @NotNull(message = "Expert ID must not be null")
    private Long expertId;
    private Long zoneId;
    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;


}
