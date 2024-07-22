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
            @Mapping(target = "zones", source = "zoneIds", qualifiedByName = "mapZoneIdsToZones"),
            @Mapping(target = "availabilities", source = "availabilityIds", qualifiedByName = "mapAvailabilityIdsToAvailabilities"),
            @Mapping(target = "bookings", source = "bookingIds", qualifiedByName = "mapBookingIdsToBookings")
    })
    Expert toEntity(ExpertDto dto);

    @Named("extractZoneIds")
    default List<Long> extractZoneIds(List<Zone> zones) {
        return zones != null ? zones.stream()
                .map(Zone::getId)
                .collect(Collectors.toList()) : List.of();
    }

    @Named("extractAvailabilityIds")
    default List<Long> extractAvailabilityIds(List<Availability> availabilities) {
        return availabilities != null ? availabilities.stream()
                .map(Availability::getId)
                .collect(Collectors.toList()) : List.of();
    }

    @Named("extractBookingIds")
    default List<Long> extractBookingIds(List<Booking> bookings) {
        return bookings != null ? bookings.stream()
                .map(Booking::getId)
                .collect(Collectors.toList()) : List.of();
    }

    @Named("mapZoneIdsToZones")
    default List<Zone> mapZoneIdsToZones(List<Long> zoneIds) {
        return zoneIds != null ? zoneIds.stream()
                .map(id -> {
                    Zone zone = new Zone();
                    zone.setId(id);
                    return zone;
                })
                .collect(Collectors.toList()) : List.of();
    }

    @Named("mapAvailabilityIdsToAvailabilities")
    default List<Availability> mapAvailabilityIdsToAvailabilities(List<Long> availabilityIds) {
        return availabilityIds != null ? availabilityIds.stream()
                .map(id -> {
                    Availability availability = new Availability();
                    availability.setId(id);
                    return availability;
                })
                .collect(Collectors.toList()) : List.of();
    }

    @Named("mapBookingIdsToBookings")
    default List<Booking> mapBookingIdsToBookings(List<Long> bookingIds) {
        return bookingIds != null ? bookingIds.stream()
                .map(id -> {
                    Booking booking = new Booking();
                    booking.setId(id);
                    return booking;
                })
                .collect(Collectors.toList()) : List.of();
    }
}
