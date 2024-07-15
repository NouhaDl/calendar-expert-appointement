package ma.autocash.booking.api.service.impl;

import lombok.SneakyThrows;
import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.dto.BookingDto;
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
import ma.autocash.booking.api.service.AvailabilityService;
import ma.autocash.booking.api.service.BookingService;
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

        try {
            Expert expert = expertRepository.findById(bookingDto.getExpertId())
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("booking.save.expert.notfound", 404, 404)));

            Zone zone = zoneRepository.findById(bookingDto.getZoneId())
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("booking.save.zone.notfound", 404, 404)));

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
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException("Error saving booking", e); // Wrap other exceptions in TechnicalException
        }
    }

    @Override
    public BookingDto updateBooking(Long id, BookingDto bookingDto) throws TechnicalException, BusinessException {
        Objects.requireNonNull(id, "Booking ID must not be null");
        Objects.requireNonNull(bookingDto, "BookingDto must not be null");

        try {
            Booking existingBooking = bookingRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("booking.update.notfound", 404, 404)));

            AvailabilityDto oldAvailabilityDto = new AvailabilityDto();
            oldAvailabilityDto.setExpertId(existingBooking.getExpert().getId());
            oldAvailabilityDto.setDate(existingBooking.getBookingDate());
            oldAvailabilityDto.setStartTime(existingBooking.getStartTime());
            oldAvailabilityDto.setEndTime(existingBooking.getEndTime());
            availabilityService.saveAvailability(oldAvailabilityDto);

            updateBookingEntity(existingBooking, bookingDto);

            Booking updatedBooking = bookingRepository.save(existingBooking);

            availabilityService.deleteAvailabilitiesByExpertAndDateAndTimeRange(
                    existingBooking.getExpert().getId(), existingBooking.getBookingDate(),
                    existingBooking.getStartTime(), existingBooking.getEndTime());

            availabilityService.deleteAvailabilitiesByExpertAndDateAndTimeRange(
                    bookingDto.getExpertId(), bookingDto.getBookingDate(),
                    bookingDto.getStartTime(), bookingDto.getEndTime());

            return bookingMapper.toDto(updatedBooking);
        } catch (BusinessException e) {
            throw e; // Rethrow BusinessException to caller
        } catch (TechnicalException | IllegalArgumentException e) {
            throw e; // Rethrow TechnicalException and IllegalArgumentException to caller
        } catch (Exception e) {
            throw new TechnicalException("Error updating booking", e); // Wrap other exceptions in TechnicalException
        }
    }

    private void updateBookingEntity(Booking booking, BookingDto bookingDto) throws TechnicalException, BusinessException {
        try {
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
                        .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("booking.update.expert.notfound", 404, 404)));
                booking.setExpert(expert);
            }

            if (bookingDto.getZoneId() != null && !Objects.equals(booking.getZone().getId(), bookingDto.getZoneId())) {
                Zone zone = zoneRepository.findById(bookingDto.getZoneId())
                        .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("booking.update.zone.notfound", 404, 404)));
                booking.setZone(zone);
            }
        } catch (BusinessException e) {
            throw e; // Rethrow BusinessException to caller
        } catch (Exception e) {
            throw new TechnicalException("Error updating booking entity", e); // Wrap other exceptions in TechnicalException
        }
    }

    @Override
    public void deleteBooking(Long id) throws TechnicalException, BusinessException {
        Objects.requireNonNull(id, "Booking ID must not be null");

        try {
            Booking booking = bookingRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("booking.delete.notfound", 404, 404)));

            AvailabilityDto availabilityDto = new AvailabilityDto();
            availabilityDto.setExpertId(booking.getExpert().getId());
            availabilityDto.setDate(booking.getBookingDate());
            availabilityDto.setStartTime(booking.getStartTime());
            availabilityDto.setEndTime(booking.getEndTime());
            availabilityService.saveAvailability(availabilityDto);

            bookingRepository.deleteById(id);

            availabilityService.deleteAvailabilitiesByExpertAndDateAndTimeRange(
                    booking.getExpert().getId(), booking.getBookingDate(),
                    booking.getStartTime(), booking.getEndTime());
        } catch (BusinessException e) {
            throw e; // Rethrow BusinessException to caller
        } catch (Exception e) {
            throw new TechnicalException("Error deleting booking", e); // Wrap other exceptions in TechnicalException
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
            throw new TechnicalException("Error retrieving bookings", e); // Wrap other exceptions in TechnicalException
        }
    }

    @Override
    public BookingDto getBookingById(Long id) throws TechnicalException, BusinessException {
        Objects.requireNonNull(id, "Booking ID must not be null");

        try {
            Booking booking = bookingRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("booking.get.notfound", 404, 404)));

            return bookingMapper.toDto(booking);
        } catch (BusinessException e) {
            throw e; // Rethrow BusinessException to caller
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving booking by id", e); // Wrap other exceptions in TechnicalException
        }
    }
}
