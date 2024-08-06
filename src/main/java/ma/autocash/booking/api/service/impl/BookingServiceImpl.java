package ma.autocash.booking.api.service.impl;

import jakarta.validation.Valid;
import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.ApiErrors;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.mapper.BookingMapper;
import ma.autocash.booking.api.mapper.ExpertMapper;
import ma.autocash.booking.api.mapper.ZoneMapper;
import ma.autocash.booking.api.provider.BookingProvider;
import ma.autocash.booking.api.provider.ExpertProvider;
import ma.autocash.booking.api.provider.ZoneProvider;
import ma.autocash.booking.api.service.AvailabilityService;
import ma.autocash.booking.api.service.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingProvider bookingProvider;
    private final BookingMapper bookingMapper;
    private final ZoneProvider zoneProvider;
    private final ExpertProvider expertProvider;
    private final ZoneMapper zoneMapper;
    private final ExpertMapper expertMapper;
    private final AvailabilityService availabilityService;

    public BookingServiceImpl(BookingProvider bookingProvider, BookingMapper bookingMapper,
                              ZoneProvider zoneProvider, ExpertProvider expertProvider,
                              ZoneMapper zoneMapper, ExpertMapper expertMapper,
                              AvailabilityService availabilityService) {
        this.bookingProvider = bookingProvider;
        this.bookingMapper = bookingMapper;
        this.zoneProvider = zoneProvider;
        this.expertProvider = expertProvider;
        this.zoneMapper = zoneMapper;
        this.expertMapper = expertMapper;
        this.availabilityService = availabilityService;
    }

    @Override
    public void saveBooking(@Valid BookingDto bookingDto) throws BusinessException {
        Booking booking = bookingMapper.toEntity(bookingDto);
        Zone zone = zoneProvider.getZoneById(bookingDto.getZone().getId());
        if (zone == null) {
            throw new BusinessException(ApiErrors.ZONE_NOT_FOUND);
        }
        booking.setZone(zone);

        Expert expert = expertProvider.getExpertById(bookingDto.getExpertId());
        if (expert == null) {
            throw new BusinessException(ApiErrors.EXPERT_NOT_FOUND);
        }
        booking.setExpert(expert);

        bookingProvider.saveBooking(booking);
        availabilityService.deleteAvailabilitiesByExpertAndDateAndTimeRange(
                booking.getExpert().getId(), booking.getBookingDate(), booking.getStartTime(), booking.getEndTime()
        );
        expert.getBookings().add(booking);
        expertProvider.updateExpert(expert);
    }

    @Override
    public void updateBooking(@Valid BookingDto bookingDto) throws BusinessException {
        Booking existingBooking = bookingProvider.getBookingById(bookingDto.getId());
        if (existingBooking == null) {
            throw new BusinessException(ApiErrors.BOOKING_NOT_FOUND);
        }

        Expert expert = expertProvider.getExpertById(bookingDto.getExpertId());
        if (expert == null) {
            throw new BusinessException(ApiErrors.EXPERT_NOT_FOUND);
        }

        Zone zone = zoneProvider.getZoneById(bookingDto.getZone().getId());
        if (zone == null) {
            throw new BusinessException(ApiErrors.ZONE_NOT_FOUND);
        }

        existingBooking.setZone(zone);
        existingBooking.setExpert(expert);
        existingBooking.setBookingDate(bookingDto.getBookingDate());
        existingBooking.setStartTime(bookingDto.getStartTime());
        existingBooking.setEndTime(bookingDto.getEndTime());

        bookingProvider.updateBooking(existingBooking);
        availabilityService.deleteAvailabilitiesByExpertAndDateAndTimeRange(
                expert.getId(), existingBooking.getBookingDate(), existingBooking.getStartTime(), existingBooking.getEndTime()
        );

        expert.getBookings().removeIf(b -> b.getId().equals(existingBooking.getId()));
        expert.getBookings().add(existingBooking);
        expertProvider.updateExpert(expert);
    }

    @Override
    public void deleteBooking(Long id) throws BusinessException {
        Booking booking = bookingProvider.getBookingById(id);
        if (booking == null) {
            throw new BusinessException(ApiErrors.BOOKING_NOT_FOUND);
        }
        bookingProvider.deleteBooking(id);
        availabilityService.createAvailabilityFromBooking(bookingMapper.toDto(booking));

        Expert expert = expertProvider.getExpertById(booking.getExpert().getId());
        if (expert != null) {
            expert.getBookings().remove(booking);
            expertProvider.updateExpert(expert);
        }
    }

    @Override
    public BookingDto getBookingById(Long id) throws BusinessException {
        Booking booking = bookingProvider.getBookingById(id);
        if (booking == null) {
            throw new BusinessException(ApiErrors.BOOKING_NOT_FOUND);
        }
        return bookingMapper.toDto(booking);
    }

    @Override
    public List<BookingDto> getAllBookings() throws BusinessException {
        List<Booking> bookings = bookingProvider.getAllBookings();
        if (bookings.isEmpty()) {
            throw new BusinessException(ApiErrors.BOOKING_NOT_FOUND);
        }
        return bookings.stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }
}
