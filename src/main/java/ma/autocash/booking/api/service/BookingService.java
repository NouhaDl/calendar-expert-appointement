package ma.autocash.booking.api.service;

import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.exception.BusinessException;


import java.util.List;

public interface BookingService {

    void saveBooking(BookingDto bookingDto) throws  BusinessException;

    void updateBooking(Long id, BookingDto bookingDto) throws  BusinessException;

    void deleteBooking(Long id) throws BusinessException;

    List<BookingDto> getAllBookings() throws  BusinessException;

    BookingDto getBookingById(Long id) throws  BusinessException;
}
