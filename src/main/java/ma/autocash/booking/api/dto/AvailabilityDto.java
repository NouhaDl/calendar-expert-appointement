package ma.autocash.booking.api.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AvailabilityDto {
    private Long id;
    @NotNull(message = "Expert ID must not be null")
    private Long expertId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

}