package ma.autocash.booking.api.mapper;
import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Zone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    @Mapping(source = "expert.id", target = "expertId")
    @Mapping(source = "zone.id", target = "zoneId")
    BookingDto toDto(Booking booking);

    @Mapping(target = "expert", ignore = true)
    @Mapping(target = "zone", ignore = true)
    void updateFromDto(BookingDto dto, @MappingTarget Booking booking);

    default Booking toEntity(BookingDto dto, Expert expert, Zone zone) {
        if (dto == null) {
            return null;
        }
        Booking booking = new Booking();

        booking.setExpert(expert);
        booking.setZone(zone);
        // Map other fields if needed
        return booking;
    }
}
