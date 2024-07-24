package ma.autocash.booking.api.mapper;
import ma.autocash.booking.api.dto.ZoneDto;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Mapper(componentModel = "spring")
public interface ZoneMapper {
    @Mappings({
            @Mapping(source = "experts", target = "expertIds", qualifiedByName = "extractExpertIds"),
            @Mapping(source = "bookings", target = "bookingIds", qualifiedByName = "extractBookingIds")
    })
    ZoneDto toDto(Zone zone);
    @Mappings({
            @Mapping(source = "expertIds", target = "experts", qualifiedByName = "toExperts"),
            @Mapping(source = "bookingIds", target = "bookings", qualifiedByName = "toBookings")
    })
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
                .map(Booking::new)
                .collect(Collectors.toList()) : Collections.emptyList();
    }
}
