package ma.autocash.booking.api.provider;

import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingProviderImpl implements BookingProvider {

    private final BookingRepository bookingRepository;

    public BookingProviderImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking saveBooking(Booking booking) {
        try {
            return bookingRepository.save(booking);
        } catch (Exception e) {
            throw new TechnicalException("Error saving booking", e);
        }
    }

    @Override
    public Booking updateBooking(Booking booking) {
        try {
            if (bookingRepository.existsById(booking.getId())) {
                return bookingRepository.save(booking);
            } else {
                throw new BusinessException("Booking not found for update");
            }
        } catch (Exception e) {
            throw new TechnicalException("Error updating booking", e);
        }
    }

    @Override
    public void deleteBooking(Long id) {
        try {
            if (bookingRepository.existsById(id)) {
                bookingRepository.deleteById(id);
            } else {
                throw new BusinessException("Booking not found for deletion");
            }
        } catch (Exception e) {
            throw new TechnicalException("Error deleting booking", e);
        }
    }

    @Override
    public List<Booking> getAllBookings() {
        try {
            return bookingRepository.findAll();
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving bookings", e);
        }
    }

    @Override
    public Booking getBookingById(Long id) {
        try {
            return bookingRepository.findById(id).orElseThrow(() -> new BusinessException("Booking not found"));
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving booking by id", e);
        }
    }
}
