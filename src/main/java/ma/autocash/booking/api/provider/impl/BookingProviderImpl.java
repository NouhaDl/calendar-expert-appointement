package ma.autocash.booking.api.provider.impl;

import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.provider.BookingProvider;
import ma.autocash.booking.api.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class BookingProviderImpl implements BookingProvider {
    private final BookingRepository bookingRepository;
    public BookingProviderImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }
    @Override
    public Booking updateBooking(Booking booking) {
        if (bookingRepository.existsById(booking.getId())) {
            return bookingRepository.save(booking);
        } else {
            throw new BusinessException("Booking not found for update");
        }
    }
    @Override
    public void deleteBooking(Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
        } else {
            throw new BusinessException("Booking not found for deletion");
        }
    }
    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Booking not found"));
    }
}
