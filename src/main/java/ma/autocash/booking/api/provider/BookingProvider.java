package ma.autocash.booking.api.provider;

import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.exception.BusinessException;

import java.util.List;

public interface BookingProvider {

    void saveBooking(Booking booking) throws BusinessException;

    void updateBooking(Booking booking) throws BusinessException;

    void deleteBooking(Long id) ;

    Booking getBookingById(Long id) throws BusinessException;

    List<Booking> getAllBookings() throws BusinessException;
}
