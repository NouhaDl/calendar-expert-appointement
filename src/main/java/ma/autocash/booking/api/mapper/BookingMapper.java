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

    @Mapping(source = "expert", target = "expertId")
    @Mapping(source = "zone", target = "zoneId")
    BookingDto toDTO(Booking booking);

    @Mapping(source = "expertId", target = "expert")
    @Mapping(source = "zoneId", target = "zone")
    Booking toEntity(BookingDto dto, Expert expert, Zone zone);

    @Mapping(source = "expertId", target = "expert")
    @Mapping(source = "zoneId", target = "zone")
    void updateFromDto(BookingDto dto, @MappingTarget Booking booking);
}
