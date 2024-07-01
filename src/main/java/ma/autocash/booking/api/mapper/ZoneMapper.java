package ma.autocash.booking.api.mapper;

import ma.autocash.booking.api.dto.ZoneDto;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ZoneMapper {

    ZoneMapper INSTANCE = Mappers.getMapper(ZoneMapper.class);

    @Mapping(target = "expertIds", source = "experts", qualifiedByName = "extractExpertIds")
    @Mapping(target = "bookingIds", source = "bookings", qualifiedByName = "extractBookingIds")
    ZoneDto toDto(Zone zone);

    @Mapping(target = "experts", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    Zone toEntity(ZoneDto dto);

    @Named("extractExpertIds")
    default List<Long> extractExpertIds(List<Expert> experts) {
        return experts.stream()
                .map(Expert::getId)
                .collect(Collectors.toList());
    }

    @Named("extractBookingIds")
    default List<Long> extractBookingIds(List<Booking> bookings) {
        return bookings.stream()
                .map(Booking::getId)
                .collect(Collectors.toList());
    }
}
