package ma.autocash.booking.api.provider.impl;

import jakarta.validation.Valid;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.exception.ApiErrors;
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
    public void saveBooking(@Valid Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    public void updateBooking(@Valid Booking booking) throws BusinessException {
        if (!bookingRepository.existsById(booking.getId())) {
            throw new BusinessException(ApiErrors.BOOKING_NOT_FOUND);
        }
        bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public Booking getBookingById(Long id) throws BusinessException {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApiErrors.BOOKING_NOT_FOUND));
    }

    @Override
    public List<Booking> getAllBookings() throws BusinessException {
        List<Booking> bookings = bookingRepository.findAll();
        if (bookings.isEmpty()) {
            throw new BusinessException(ApiErrors.BOOKING_NOT_FOUND);
        }
        return bookings;
    }

    @Override
    public List<Booking> getBookingsByIds(List<Long> ids) {
        return bookingRepository.findAllById(ids);
    }
}
