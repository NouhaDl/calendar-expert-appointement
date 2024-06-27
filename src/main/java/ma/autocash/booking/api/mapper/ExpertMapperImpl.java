package ma.autocash.booking.api.mapper;

import ma.autocash.booking.api.dto.ExpertDto;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.entity.Zone;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExpertMapperImpl implements ExpertMapper {

    @Override
    public ExpertDto toDTO(Expert expert) {
        if (expert == null) {
            return null;
        }

        ExpertDto dto = new ExpertDto();
        dto.setId(expert.getId());
        dto.setFirstName(expert.getFirstName());
        dto.setLastName(expert.getLastName());
        dto.setZoneIds(mapZoneIds(expert.getZones()));
        dto.setAvailabilityIds(mapAvailabilityIds(expert.getAvailabilities()));
        dto.setBookingIds(mapBookingIds(expert.getBookings()));

        return dto;
    }

    @Override
    public Expert toEntity(ExpertDto dto, List<Zone> zones) {
        if (dto == null) {
            return null;
        }

        Expert expert = new Expert();
        expert.setId(dto.getId());
        expert.setFirstName(dto.getFirstName());
        expert.setLastName(dto.getLastName());

        expert.setZones(zones);

        return expert;
    }

    @Override
    public void updateFromDto(ExpertDto dto, Expert expert, List<Zone> zones) {
        if (dto == null || expert == null) {
            return;
        }

        // Update the expert entity fields from DTO
        expert.setFirstName(dto.getFirstName());
        expert.setLastName(dto.getLastName());
        // Update other fields as needed

        // Set the zones
        expert.setZones(zones);
    }

    // methods to map IDs from entities to DTO
    private List<Long> mapZoneIds(List<Zone> zones) {
        return zones.stream()
                .map(Zone::getId)
                .collect(Collectors.toList());
    }

    private List<Long> mapAvailabilityIds(List<Availability> availabilities) {
        return availabilities.stream()
                .map(Availability::getId)
                .collect(Collectors.toList());
    }

    private List<Long> mapBookingIds(List<Booking> bookings) {
        return bookings.stream()
                .map(Booking::getId)
                .collect(Collectors.toList());
    }
}
