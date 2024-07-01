package ma.autocash.booking.api.services.impl;

import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.mapper.BookingMapper;
import ma.autocash.booking.api.repository.BookingRepository;
import ma.autocash.booking.api.repository.ExpertRepository;
import ma.autocash.booking.api.repository.ZoneRepository;
import ma.autocash.booking.api.services.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ExpertRepository expertRepository;
    private final ZoneRepository zoneRepository;
    private final BookingMapper bookingMapper;

    public BookingServiceImpl(BookingRepository bookingRepository, ExpertRepository expertRepository, ZoneRepository zoneRepository, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.expertRepository = expertRepository;
        this.zoneRepository = zoneRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public BookingDto saveBooking(BookingDto bookingDto) {
        try {
            Expert expert = expertRepository.findById(bookingDto.getExpertId())
                    .orElseThrow(() -> new BusinessException("Expert not found"));
            Zone zone = zoneRepository.findById(bookingDto.getZoneId())
                    .orElseThrow(() -> new BusinessException("Zone not found"));

            Booking booking = bookingMapper.toEntity(bookingDto, expert, zone);
            Booking savedBooking = bookingRepository.save(booking);
            return bookingMapper.toDto(savedBooking);
        } catch (Exception e) {
            throw new TechnicalException("Error saving booking", e);
        }
    }

    @Override
    public BookingDto updateBooking(Long id, BookingDto bookingDto) {
        try {
            Booking existingBooking = bookingRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Booking not found for update"));

            Expert expert = expertRepository.findById(bookingDto.getExpertId())
                    .orElseThrow(() -> new BusinessException("Expert not found"));
            Zone zone = zoneRepository.findById(bookingDto.getZoneId())
                    .orElseThrow(() -> new BusinessException("Zone not found"));

            bookingMapper.updateFromDto(bookingDto, existingBooking);

            existingBooking.setExpert(expert);
            existingBooking.setZone(zone);

            bookingRepository.save(existingBooking);

            return bookingMapper.toDto(existingBooking);
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
                    .map(bookingMapper::toDto)
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
            return bookingMapper.toDto(booking);
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving booking by id", e);
        }
    }
}
