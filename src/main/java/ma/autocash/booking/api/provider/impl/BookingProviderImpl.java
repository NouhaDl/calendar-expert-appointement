package ma.autocash.booking.api.provider.impl;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.KeyValueErrorImpl;
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
    // TODO: Please check all exceptions and only keep necessary ones
    @Override
    public Booking saveBooking(Booking booking) throws BusinessException {
        try {
            return bookingRepository.save(booking);
        } catch (Exception e) {
            throw new BusinessException(new KeyValueErrorImpl("booking.cannot.be saved", 404, 404));
        }
    }
    @Override
    public Booking updateBooking(Booking booking) throws BusinessException {
        try {
            if (bookingRepository.existsById(booking.getId())) {
                return bookingRepository.save(booking);
            } else {
                throw new BusinessException(new KeyValueErrorImpl("booking.update.notfound", 404, 404));
            }
        } catch (Exception e) {
            throw new BusinessException(new KeyValueErrorImpl("booking.cannot.be updated", 404, 404));
        }
    }
    @Override
    public void deleteBooking(Long id) throws BusinessException {
        try {
            if (bookingRepository.existsById(id)) {
                bookingRepository.deleteById(id);
            } else {
                throw new BusinessException(new KeyValueErrorImpl("booking.delete.notfound", 404, 404));
            }
        } catch (Exception e) {
            throw new BusinessException(new KeyValueErrorImpl("booking.delete.notfound", 404, 404));
        }
    }
    @Override
    public List<Booking> getAllBookings() throws BusinessException {
        try {
            return bookingRepository.findAll();
        } catch (Exception e) {
            throw new BusinessException(new KeyValueErrorImpl("booking.get.notfound", 404, 404));
        }
    }

    @Override
    public Booking getBookingById(Long id) throws BusinessException {
        try {
            return bookingRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("booking.get.notfound", 404, 404)));
        } catch (Exception e) {
            throw new BusinessException(new KeyValueErrorImpl("booking.get.notfound", 404, 404));
        }
    }
}
