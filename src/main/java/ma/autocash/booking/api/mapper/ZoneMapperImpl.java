package ma.autocash.booking.api.mapper;

import ma.autocash.booking.api.dto.ZoneDto;
import ma.autocash.booking.api.entity.Zone;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ZoneMapperImpl implements ZoneMapper {

    @Override
    public ZoneDto toDto(Zone zone) {
        if (zone == null) {
            return null;
        }
        ZoneDto dto = new ZoneDto();
        dto.setId(zone.getId());
        dto.setName(zone.getName());
        dto.setExpertIds(zone.getExperts().stream()
                .map(expert -> expert.getId())
                .collect(Collectors.toList()));
        dto.setBookingIds(zone.getBookings().stream()
                .map(booking -> booking.getId())
                .collect(Collectors.toList()));
        return dto;
    }

    @Override
    public Zone toEntity(ZoneDto dto) {
        if (dto == null) {
            return null;
        }
        Zone zone = new Zone();
        zone.setId(dto.getId());
        zone.setName(dto.getName());
        // Note: You may need to fetch and set experts and bookings based on their IDs here
        return zone;
    }

    @Override
    public void updateFromDto(ZoneDto dto, Zone zone) {
        if (dto != null && zone != null) {
            zone.setName(dto.getName());
            // Update experts and bookings based on their IDs
            // Example: Fetch and set experts and bookings based on expertIds and bookingIds
            // This part depends on how you manage relationships in your application
        }
    }
}
