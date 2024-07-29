package ma.autocash.booking.api.provider.impl;

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
    public void saveBooking(Booking booking){
        bookingRepository.save(booking);
    }

    @Override
    public void updateBooking(Booking booking) throws BusinessException {
        if (bookingRepository.existsById(booking.getId())) {
            bookingRepository.save(booking);
        } else {
            throw new BusinessException(ApiErrors.BOOKING_NOT_FOUND);
        }
    }

    @Override
    public void deleteBooking(Long id)  {
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
}
