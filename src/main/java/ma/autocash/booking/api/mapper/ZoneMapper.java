package ma.autocash.booking.api.mapper;

import ma.autocash.booking.api.dto.ZoneDto;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ZoneMapper {

    @Mapping(target = "expertIds", source = "experts", qualifiedByName = "extractExpertIds")
    @Mapping(target = "bookingIds", source = "bookings", qualifiedByName = "extractBookingIds")
    ZoneDto toDto(Zone zone);

    @Mapping(target = "experts", source = "expertIds", qualifiedByName = "toExperts")
    @Mapping(target = "bookings", source = "bookingIds", qualifiedByName = "toBookings")
    Zone toEntity(ZoneDto dto);

    @Named("extractExpertIds")
    default List<Long> extractExpertIds(List<Expert> experts) {
        return experts != null ? experts.stream()
                .map(Expert::getId)
                .collect(Collectors.toList()) : Collections.emptyList();
    }

    @Named("extractBookingIds")
    default List<Long> extractBookingIds(List<Booking> bookings) {
        return bookings != null ? bookings.stream()
                .map(Booking::getId)
                .collect(Collectors.toList()) : Collections.emptyList();
    }

    @Named("toExperts")
    default List<Expert> toExperts(List<Long> expertIds) {
        return expertIds != null ? expertIds.stream()
                .map(id -> {
                    Expert expert = new Expert();
                    expert.setId(id);
                    return expert;
                })
                .collect(Collectors.toList()) : Collections.emptyList();
    }

    @Named("toBookings")
    default List<Booking> toBookings(List<Long> bookingIds) {
        return bookingIds != null ? bookingIds.stream()
                .map(id -> {
                    Booking booking = new Booking();
                    booking.setId(id);
                    return booking;
                })
                .collect(Collectors.toList()) : Collections.emptyList();
    }
}
