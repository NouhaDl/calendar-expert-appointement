package ma.autocash.booking.api.service;

import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.exception.TechnicalException;

import java.util.List;

public interface BookingService {

    BookingDto saveBooking(BookingDto bookingDto) throws TechnicalException;

    BookingDto updateBooking(Long id, BookingDto bookingDto) throws TechnicalException;

    void deleteBooking(Long id) throws TechnicalException;

    List<BookingDto> getAllBookings() throws TechnicalException;

    BookingDto getBookingById(Long id) throws TechnicalException;


}