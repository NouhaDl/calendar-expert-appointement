package ma.autocash.booking.api.mapper;

import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Zone;
import org.springframework.stereotype.Component;

@Component
public class BookingMapperImpl implements BookingMapper {

    @Override
    public BookingDto toDTO(Booking booking) {
        if (booking == null) {
            return null;
        }

        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setExpertId(booking.getExpert().getId());
        dto.setZoneId(booking.getZone().getId());
        dto.setBookingTime(booking.getBookingTime());

        return dto;
    }

    @Override
    public Booking toEntity(BookingDto dto, Expert expert, Zone zone) {
        if (dto == null) {
            return null;
        }

        Booking booking = new Booking();
        booking.setId(dto.getId());
        booking.setExpert(expert);
        booking.setZone(zone);
        booking.setBookingTime(dto.getBookingTime());

        return booking;
    }

    @Override
    public void updateFromDto(BookingDto dto, Booking booking) {
        if (dto == null || booking == null) {
            return;
        }

        booking.setId(dto.getId());
        booking.setBookingTime(dto.getBookingTime());
        // Assuming expert and zone are not updated directly from DTO in update scenario
    }
}
