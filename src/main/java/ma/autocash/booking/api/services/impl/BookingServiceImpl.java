package ma.autocash.booking.api.services.impl;

import lombok.SneakyThrows;
import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.KeyValueErrorImpl;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.mapper.BookingMapper;
import ma.autocash.booking.api.repository.BookingRepository;
import ma.autocash.booking.api.repository.ExpertRepository;
import ma.autocash.booking.api.repository.ZoneRepository;

import ma.autocash.booking.api.services.BookingService;
import ma.autocash.booking.api.services.AvailabilityService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @SneakyThrows
    @Override
    public BookingDto saveBooking(BookingDto bookingDto) throws TechnicalException {
        Objects.requireNonNull(bookingDto, "BookingDto must not be null");

        Objects.requireNonNull(bookingDto.getExpertId(), "Expert ID must not be null");
        Objects.requireNonNull(bookingDto.getZoneId(), "Zone ID must not be null");

        Expert expert = expertRepository.findById(bookingDto.getExpertId())
                .orElseThrow(() -> new IllegalArgumentException("Expert not found with ID: " + bookingDto.getExpertId()));

        Zone zone = zoneRepository.findById(bookingDto.getZoneId())
                .orElseThrow(() -> new IllegalArgumentException("Zone not found with ID: " + bookingDto.getZoneId()));

        Booking bookingEntity = bookingMapper.toEntity(bookingDto);

        bookingEntity.setExpert(expert);
        bookingEntity.setZone(zone);

        Booking savedBooking = bookingRepository.save(bookingEntity);

        availabilityService.deleteAvailabilitiesByExpertAndDateAndTimeRange(
                bookingDto.getExpertId(), bookingDto.getBookingDate(),
                bookingDto.getStartTime(), bookingDto.getEndTime());

        BookingDto savedBookingDto = bookingMapper.toDto(savedBooking);
        savedBookingDto.setId(savedBooking.getId());

        return savedBookingDto;
    }

    @SneakyThrows
    @Override
    public BookingDto updateBooking(Long id, BookingDto bookingDto) throws TechnicalException {
        Objects.requireNonNull(id, "Booking ID must not be null");
        Objects.requireNonNull(bookingDto, "BookingDto must not be null");

        try {
            // Récupérer les informations de l'ancienne réservation avant de la mettre à jour
            Booking existingBooking = bookingRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("booking.update.notfound", 404, 404)));

            // Restaurer les disponibilités basées sur les informations de l'ancienne réservation
            AvailabilityDto oldAvailabilityDto = new AvailabilityDto();
            oldAvailabilityDto.setExpertId(existingBooking.getExpert().getId());
            oldAvailabilityDto.setDate(existingBooking.getBookingDate());
            oldAvailabilityDto.setStartTime(existingBooking.getStartTime());
            oldAvailabilityDto.setEndTime(existingBooking.getEndTime());

            availabilityService.saveAvailability(oldAvailabilityDto);

            // Mettre à jour la réservation avec les nouvelles informations
            Expert expert = expertRepository.findById(bookingDto.getExpertId())
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("booking.update.notfound" + bookingDto.getExpertId(), 404, 404)));

            Zone zone = zoneRepository.findById(bookingDto.getZoneId())
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("zone.get.notfound" + bookingDto.getExpertId(), 404, 404)));

            existingBooking.setBookingDate(bookingDto.getBookingDate());
            existingBooking.setStartTime(bookingDto.getStartTime());
            existingBooking.setEndTime(bookingDto.getEndTime());
            existingBooking.setExpert(expert);
            existingBooking.setZone(zone);

            Booking updatedBooking = bookingRepository.save(existingBooking);

            // Supprimer l availability correspondant à la nouvelle réservation mise a jour
            availabilityService.deleteAvailabilitiesByExpertAndDateAndTimeRange(
                    existingBooking.getExpert().getId(), existingBooking.getBookingDate(),
                    existingBooking.getStartTime(), existingBooking.getEndTime());

            return bookingMapper.toDto(updatedBooking);
        } catch (Exception e) {
            throw new TechnicalException("Error updating booking", e);
        }
    }

    @SneakyThrows
    @Override
    public void deleteBooking(Long id) throws TechnicalException {
        try {
            Booking booking = bookingRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("booking.delete.notfound", 404, 404)));

            bookingRepository.deleteById(id);
            AvailabilityDto availabilityDto = new AvailabilityDto();
            availabilityDto.setExpertId(booking.getExpert().getId());
            availabilityDto.setDate(booking.getBookingDate());
            availabilityDto.setStartTime(booking.getStartTime());
            availabilityDto.setEndTime(booking.getEndTime());

            availabilityService.saveAvailability(availabilityDto);

        } catch (Exception e) {
            throw new TechnicalException("Error deleting booking", e);
        }
    }

    @SneakyThrows
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

    @SneakyThrows
    @Override
    public BookingDto getBookingById(Long id) throws TechnicalException {
        try {
            Booking booking = bookingRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("booking.get.notfound", 404, 404)));

            return bookingMapper.toDto(booking);
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving booking by id", e);
        }
    }
}
