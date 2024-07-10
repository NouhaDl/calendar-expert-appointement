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

    @Mappings({
            @Mapping(target = "zones", ignore = true),
            @Mapping(target = "availabilities", ignore = true),
            @Mapping(target = "bookings", ignore = true)
    })
    Expert toEntity(ExpertDto dto);

    @Named("toEntityWithZones")
    default Expert toEntityWithZones(ExpertDto dto, List<Zone> zones) {
        Expert expert = toEntity(dto);
        if (zones != null) {
            expert.setZones(zones);
        }
        return expert;
    }

    @Named("extractZoneIds")
    default List<Long> extractZoneIds(List<Zone> zones) {
        if (zones != null) {
            return zones.stream()
                    .map(Zone::getId)
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Named("extractAvailabilityIds")
    default List<Long> extractAvailabilityIds(List<Availability> availabilities) {
        if (availabilities != null) {
            return availabilities.stream()
                    .map(Availability::getId)
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Named("extractBookingIds")
    default List<Long> extractBookingIds(List<Booking> bookings) {
        if (bookings != null) {
            return bookings.stream()
                    .map(Booking::getId)
                    .collect(Collectors.toList());
        }
        return null;
    }
}
