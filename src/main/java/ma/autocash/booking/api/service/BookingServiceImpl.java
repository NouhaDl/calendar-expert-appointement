package ma.autocash.booking.api.service;

import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.mapper.BookingMapper;
import ma.autocash.booking.api.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public BookingServiceImpl(BookingRepository bookingRepository, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public BookingDto saveBooking(BookingDto bookingDto) {
        try {
            Booking booking = bookingMapper.toEntity(bookingDto, new Expert(), new Zone()); // Création d'une nouvelle instance d'Expert et de Zone
            Booking savedBooking = bookingRepository.save(booking);
            return bookingMapper.toDTO(savedBooking);
        } catch (Exception e) {
            throw new TechnicalException("Error saving booking", e);
        }
    }

    @Override
    public BookingDto updateBooking(Long id, BookingDto bookingDto) {
        try {
            Booking existingBooking = bookingRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Booking not found for update"));

            // Mapping des données du DTO à l'entité existante
            bookingMapper.updateFromDto(bookingDto, existingBooking, new Expert(), new Zone());

            bookingRepository.save(existingBooking); // Sauvegarde de l'entité mise à jour

            return bookingMapper.toDTO(existingBooking);
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
    public List<BookingDto> getAllBookings() {
        try {
            List<Booking> bookings = bookingRepository.findAll();
            return bookings.stream()
                    .map(bookingMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving bookings", e);
        }
    }

    @Override
    public BookingDto getBookingById(Long id) {
        try {
            Booking booking = bookingRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Booking not found"));
            return bookingMapper.toDTO(booking);
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving booking by id", e);
        }
    }
}
