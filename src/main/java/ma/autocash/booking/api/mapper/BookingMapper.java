package ma.autocash.booking.api.mapper;
import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.entity.Expert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mappings({
            @Mapping(source = "expert", target = "expertId", qualifiedByName = "extractExpertId"),
            @Mapping(source = "zone", target = "zoneId", qualifiedByName = "extractZoneId")
    })
    BookingDto toDto(Booking booking);

    @Mappings({
            @Mapping(target = "expert", source = "expertId", qualifiedByName = "mapExpertIdToExpert"),
            @Mapping(target = "zone", source = "zoneId", qualifiedByName = "mapZoneIdToZone")
    })
    Booking toEntity(BookingDto bookingDto);

    @Named("extractExpertId")
    default Long extractExpertId(Expert expert) {
        return expert != null ? expert.getId() : null;
    }
    @Named("extractZoneId")
    default Long extractZoneId(Zone zone) {
        return zone != null ? zone.getId() : null;
    }
    @Named("mapExpertIdToExpert")
    default Expert mapExpertIdToExpert(Long expertId) {
        if (expertId == null) {
            return null;
        }
        Expert expert = new Expert();
        expert.setId(expertId);
        return expert;
    }
    @Named("mapZoneIdToZone")
    default Zone mapZoneIdToZone(Long zoneId) {
        if (zoneId == null) {
            return null;
        }
        Zone zone = new Zone();
        zone.setId(zoneId);
        return zone;
    }
}