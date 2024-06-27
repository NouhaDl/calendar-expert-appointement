package com.AutocashApplication.rdv_expert.mapper;

import com.AutocashApplication.rdv_expert.dto.BookingDto;
import com.AutocashApplication.rdv_expert.entity.Booking;
import com.AutocashApplication.rdv_expert.entity.Expert;
import com.AutocashApplication.rdv_expert.entity.Zone;

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
}
