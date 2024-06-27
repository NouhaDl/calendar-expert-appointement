package com.AutocashApplication.rdv_expert.mapper;
import com.AutocashApplication.rdv_expert.dto.BookingDto;
import com.AutocashApplication.rdv_expert.entity.Booking;
import com.AutocashApplication.rdv_expert.entity.Expert;
import com.AutocashApplication.rdv_expert.entity.Zone;
public interface BookingMapper {
    BookingDto toDTO(Booking booking);

    Booking toEntity(BookingDto dto, Expert expert, Zone zone);
}
