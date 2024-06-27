package ma.autocash.booking.api.dto;

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
    private List<Long> expertIds;
    private List<Long> bookingIds;

    // Constructor with id and name
    public ZoneDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
