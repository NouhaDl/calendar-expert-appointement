package ma.autocash.booking.api.mapper;

import ma.autocash.booking.api.dto.ExpertDto;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.entity.Availability;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ExpertMapper {

    @Mappings({

            @Mapping(source = "zones", target = "zoneIds", qualifiedByName = "extractZoneIds"),
            @Mapping(source = "availabilities", target = "availabilityIds", qualifiedByName = "extractAvailabilityIds"),
            @Mapping(source = "bookings", target = "bookingIds", qualifiedByName = "extractBookingIds")
    })
    ExpertDto toDto(Expert expert);

    Expert toEntity(ExpertDto dto);

    @Named("extractZoneIds")
    default List<Long> extractZoneIds(List<Zone> zones) {
        if (zones != null && !zones.isEmpty()) {
            return zones.stream()
                    .map(Zone::getId)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    @Named("extractAvailabilityIds")
    default List<Long> extractAvailabilityIds(List<Availability> availabilities) {
        if (availabilities != null && !availabilities.isEmpty()) {
            return availabilities.stream()
                    .map(Availability::getId)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    @Named("extractBookingIds")
    default List<Long> extractBookingIds(List<Booking> bookings) {
        if (bookings != null && !bookings.isEmpty()) {
            return bookings.stream()
                    .map(Booking::getId)
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}
