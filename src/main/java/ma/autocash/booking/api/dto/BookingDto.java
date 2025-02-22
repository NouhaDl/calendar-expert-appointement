package ma.autocash.booking.api.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingDto {

    private Long id;



    @NotNull(message = "Zone must not be null")
    private ZoneDto zone;

    private LocalDate bookingDate;

    private LocalTime startTime;

    private LocalTime endTime;

    Long expertId;
}

