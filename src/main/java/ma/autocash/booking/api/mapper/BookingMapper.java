package ma.autocash.booking.api.mapper;

import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.entity.Booking;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingDto toDto(Booking booking);

    Booking toEntity(BookingDto bookingDto);


    List<BookingDto> toDto(List<Booking> bookings);

    List<Booking> toEntityList(List<BookingDto> bookingDtos);

}