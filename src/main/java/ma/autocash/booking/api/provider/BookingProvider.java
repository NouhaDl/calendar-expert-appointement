package ma.autocash.booking.api.provider;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.exception.BusinessException;
import java.util.List;
public interface BookingProvider {
    Booking saveBooking(Booking booking) throws BusinessException;
    Booking updateBooking(Booking booking) throws BusinessException;
    void deleteBooking(Long id) throws BusinessException;
    List<Booking> getAllBookings() throws BusinessException;
    Booking getBookingById(Long id) throws BusinessException;

}
