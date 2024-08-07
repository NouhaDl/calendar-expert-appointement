package ma.autocash.booking.api.mapper;

import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.dto.BookingResponseDto;
import ma.autocash.booking.api.entity.Booking;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingDto toDto(Booking booking);

    BookingResponseDto toResponseDto(Booking booking);

    Booking toResponseEntity(BookingResponseDto bookingResponseDto);

    Booking toEntity(BookingDto bookingDto);


}
