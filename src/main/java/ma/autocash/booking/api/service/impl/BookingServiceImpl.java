package ma.autocash.booking.api.service.impl;

import jakarta.validation.Valid;
import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.mapper.BookingMapper;
import ma.autocash.booking.api.mapper.AvailabilityMapper;
import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.provider.BookingProvider;
import ma.autocash.booking.api.provider.ExpertProvider;
import ma.autocash.booking.api.provider.ZoneProvider;
import ma.autocash.booking.api.provider.AvailabilityProvider;
import ma.autocash.booking.api.service.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingProvider bookingProvider;
    private final ExpertProvider expertProvider;
    private final ZoneProvider zoneProvider;
    private final BookingMapper bookingMapper;
    private final AvailabilityMapper availabilityMapper;
    private final AvailabilityProvider availabilityProvider;

    public BookingServiceImpl(BookingProvider bookingProvider, ExpertProvider expertProvider,
                              ZoneProvider zoneProvider, BookingMapper bookingMapper,
                              AvailabilityProvider availabilityProvider, AvailabilityMapper availabilityMapper) {
        this.bookingProvider = bookingProvider;
        this.expertProvider = expertProvider;
        this.zoneProvider = zoneProvider;
        this.bookingMapper = bookingMapper;
        this.availabilityProvider = availabilityProvider;
        this.availabilityMapper = availabilityMapper;
    }

    @Override
    public void saveBooking(@Valid BookingDto bookingDto) throws  BusinessException {
        Expert expert = expertProvider.getExpertById(bookingDto.getExpertId());
        Zone zone = zoneProvider.getZoneById(bookingDto.getZoneId());
        Booking booking = bookingMapper.toEntity(bookingDto);
        booking.setExpert(expert);
        booking.setZone(zone);
        bookingProvider.saveBooking(booking);
        availabilityProvider.deleteAvailabilitiesByExpertAndDateAndTimeRange(
                bookingDto.getExpertId(), bookingDto.getBookingDate(),
                bookingDto.getStartTime(), bookingDto.getEndTime());
    }

    @Override
    public void updateBooking(Long id, @Valid BookingDto bookingDto) throws  BusinessException {
        Booking existingBooking = bookingProvider.getBookingById(id);
        updateBookingEntity(existingBooking, bookingDto);
        bookingProvider.updateBooking(existingBooking);
        availabilityProvider.deleteAvailabilitiesByExpertAndDateAndTimeRange(
                bookingDto.getExpertId(), bookingDto.getBookingDate(),
                bookingDto.getStartTime(), bookingDto.getEndTime());
    }

    @Override
    public void deleteBooking(Long id) throws BusinessException {
        Booking booking = bookingProvider.getBookingById(id);
        bookingProvider.deleteBooking(id);
        AvailabilityDto availabilityDto = new AvailabilityDto();
        availabilityDto.setExpertId(booking.getExpert().getId());
        availabilityDto.setDate(booking.getBookingDate());
        availabilityDto.setStartTime(booking.getStartTime());
        availabilityDto.setEndTime(booking.getEndTime());
        Availability availability = availabilityMapper.toEntity(availabilityDto);
        availabilityProvider.addExpertAvailability(availability, booking.getExpert().getId());
    }

    @Override
    public List<BookingDto> getAllBookings() throws  BusinessException {
        List<Booking> bookings = bookingProvider.getAllBookings();
        return bookingMapper.toDto(bookings);
    }

    @Override
    public BookingDto getBookingById(Long id) throws  BusinessException {
        Booking booking = bookingProvider.getBookingById(id);
        return bookingMapper.toDto(booking);
    }

    private void updateBookingEntity(Booking existingBooking, BookingDto bookingDto) {
        if (bookingDto.getExpertId() != null) {
            Expert expert = new Expert();
            expert.setId(bookingDto.getExpertId());
            existingBooking.setExpert(expert);
        }
        if(bookingDto.getZoneId() != null) {
            Zone zone = new Zone();
            zone.setId(bookingDto.getZoneId());
            existingBooking.setZone(zone);
        }
        existingBooking.setBookingDate(bookingDto.getBookingDate());
        existingBooking.setStartTime(bookingDto.getStartTime());
        existingBooking.setEndTime(bookingDto.getEndTime());
    }
}
