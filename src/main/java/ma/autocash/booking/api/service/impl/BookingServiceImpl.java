package ma.autocash.booking.api.service.impl;

import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.EntityNotFoundException;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.mapper.BookingMapper;
import ma.autocash.booking.api.repository.BookingRepository;
import ma.autocash.booking.api.repository.ExpertRepository;
import ma.autocash.booking.api.repository.ZoneRepository;
import ma.autocash.booking.api.service.AvailabilityService;
import ma.autocash.booking.api.service.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Service
@Transactional
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ExpertRepository expertRepository;
    private final ZoneRepository zoneRepository;
    private final BookingMapper bookingMapper;
    private final AvailabilityService availabilityService;

    public BookingServiceImpl(BookingRepository bookingRepository, ExpertRepository expertRepository,
                              ZoneRepository zoneRepository, BookingMapper bookingMapper,
                              AvailabilityService availabilityService) {
        this.bookingRepository = bookingRepository;
        this.expertRepository = expertRepository;
        this.zoneRepository = zoneRepository;
        this.bookingMapper = bookingMapper;
        this.availabilityService = availabilityService;
    }
    @Override
    public BookingDto saveBooking(@Valid BookingDto bookingDto) throws TechnicalException {
        try {
            Objects.requireNonNull(bookingDto, "BookingDto must not be null");
            Objects.requireNonNull(bookingDto.getExpertId(), "Expert ID must not be null");
            Objects.requireNonNull(bookingDto.getZoneId(), "Zone ID must not be null");

            Expert expert = expertRepository.findById(bookingDto.getExpertId())
                    .orElseThrow(() -> new EntityNotFoundException("Expert", bookingDto.getExpertId()));
            Zone zone = zoneRepository.findById(bookingDto.getZoneId())
                    .orElseThrow(() -> new EntityNotFoundException("Zone", bookingDto.getZoneId()));

            Booking bookingEntity = bookingMapper.toEntity(bookingDto);
            bookingEntity.setExpert(expert);
            bookingEntity.setZone(zone);

            // Save the booking
            Booking savedBooking = bookingRepository.save(bookingEntity);

            // Delete overlapping availabilities
            availabilityService.deleteAvailabilitiesByExpertAndDateAndTimeRange(
                    bookingDto.getExpertId(), bookingDto.getBookingDate(),
                    bookingDto.getStartTime(), bookingDto.getEndTime());

            BookingDto savedBookingDto = bookingMapper.toDto(savedBooking);
            savedBookingDto.setId(savedBooking.getId());
            return savedBookingDto;
        } catch (Exception e) {
            throw new TechnicalException("Error saving booking", e);
        }
    }
    @Override
    public BookingDto updateBooking(Long id, @Valid BookingDto bookingDto) throws TechnicalException {
        try {
            Booking existingBooking = bookingRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Booking", id));
            // Delete old overlapping availabilities
            availabilityService.deleteAvailabilitiesByExpertAndDateAndTimeRange(
                    existingBooking.getExpert().getId(), existingBooking.getBookingDate(),
                    existingBooking.getStartTime(), existingBooking.getEndTime());
            // Update booking entity fields
            updateBookingEntity(existingBooking, bookingDto);

            // Save the updated booking
            Booking updatedBooking = bookingRepository.save(existingBooking);

            // Delete new overlapping availabilities
            availabilityService.deleteAvailabilitiesByExpertAndDateAndTimeRange(
                    bookingDto.getExpertId(), bookingDto.getBookingDate(),
                    bookingDto.getStartTime(), bookingDto.getEndTime());

            return bookingMapper.toDto(updatedBooking);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException("Error updating booking", e);
        }
    }

    @Override
    public void deleteBooking(Long id) throws TechnicalException {
        try {
            Booking booking = bookingRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Booking", id));
            // Save availability before deleting booking
            AvailabilityDto availabilityDto = new AvailabilityDto();
            availabilityDto.setExpertId(booking.getExpert().getId());
            availabilityDto.setDate(booking.getBookingDate());
            availabilityDto.setStartTime(booking.getStartTime());
            availabilityDto.setEndTime(booking.getEndTime());

            // Delete booking
            bookingRepository.deleteById(id);

            // Save availability after deleting booking
            availabilityService.saveAvailability(availabilityDto);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException("Error deleting booking", e);
        }
    }

    @Override
    public List<BookingDto> getAllBookings() throws TechnicalException {
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
    public BookingDto getBookingById(Long id) throws TechnicalException {
        try {
            Booking booking = bookingRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Booking", id));
            return bookingMapper.toDto(booking);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving booking by id", e);
        }
    }

    private void updateBookingEntity(Booking booking, BookingDto bookingDto) {
        if (bookingDto.getBookingDate() != null) {
            booking.setBookingDate(bookingDto.getBookingDate());
        }
        if (bookingDto.getStartTime() != null) {
            booking.setStartTime(bookingDto.getStartTime());
        }
        if (bookingDto.getEndTime() != null) {
            booking.setEndTime(bookingDto.getEndTime());
        }
        if (bookingDto.getExpertId() != null && !Objects.equals(booking.getExpert().getId(), bookingDto.getExpertId())) {
            Expert expert = expertRepository.findById(bookingDto.getExpertId())
                    .orElseThrow(() -> new EntityNotFoundException("Expert", bookingDto.getExpertId()));
            booking.setExpert(expert);
        }
        if (bookingDto.getZoneId() != null && !Objects.equals(booking.getZone().getId(), bookingDto.getZoneId())) {
            Zone zone = zoneRepository.findById(bookingDto.getZoneId())
                    .orElseThrow(() -> new EntityNotFoundException("Zone", bookingDto.getZoneId()));
            booking.setZone(zone);
        }
    }
}
