package ma.autocash.booking.api.mapper;

import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.entity.Booking;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {


    BookingDto toDto(Booking booking);




    Booking toEntity(BookingDto bookingDto);
}
