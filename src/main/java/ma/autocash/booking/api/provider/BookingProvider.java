package ma.autocash.booking.api.provider;

import jakarta.validation.Valid;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.exception.BusinessException;

import java.util.List;

public interface BookingProvider {

    void saveBooking(@Valid Booking booking);

    void updateBooking(@Valid Booking booking) throws BusinessException;

    void deleteBooking(Long id);

    Booking getBookingById(Long id) throws BusinessException;

    List<Booking> getAllBookings() throws BusinessException;

    List<Booking> getBookingsByIds(List<Long> ids);
}
