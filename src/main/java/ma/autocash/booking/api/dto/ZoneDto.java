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

}