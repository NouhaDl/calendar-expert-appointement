package ma.autocash.booking.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityDto {

    private Long id;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private ZoneDto zone;

     Long expertId;



}

