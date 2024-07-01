package ma.autocash.booking.api.services;

import ma.autocash.booking.api.dto.BookingDto;

import java.util.List;

public interface BookingService {

    BookingDto saveBooking(BookingDto bookingDto);

    BookingDto updateBooking(Long id, BookingDto bookingDto);

    void deleteBooking(Long id);

    List<BookingDto> getAllBookings();

    BookingDto getBookingById(Long id);
}
