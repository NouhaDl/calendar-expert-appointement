package ma.autocash.booking.api.provider;

import ma.autocash.booking.api.entity.Booking;

import java.util.List;

public interface BookingProvider {
    Booking saveBooking(Booking booking);
    Booking updateBooking(Booking booking);
    void deleteBooking(Long id);
    List<Booking> getAllBookings();
    Booking getBookingById(Long id);
}
