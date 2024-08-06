package ma.autocash.booking.api.service.impl;

import jakarta.validation.Valid;
import ma.autocash.booking.api.dto.ExpertDto;
import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.ApiErrors;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.mapper.ExpertMapper;
import ma.autocash.booking.api.mapper.AvailabilityMapper;
import ma.autocash.booking.api.mapper.BookingMapper;
import ma.autocash.booking.api.provider.ExpertProvider;
import ma.autocash.booking.api.provider.ZoneProvider;
import ma.autocash.booking.api.service.ExpertService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExpertServiceImpl implements ExpertService {

    private final ExpertProvider expertProvider;
    private final ExpertMapper expertMapper;
    private final ZoneProvider zoneProvider;
    private final AvailabilityMapper availabilityMapper;
    private final BookingMapper bookingMapper;

    public ExpertServiceImpl(ExpertProvider expertProvider, ExpertMapper expertMapper, ZoneProvider zoneProvider,
                             AvailabilityMapper availabilityMapper, BookingMapper bookingMapper) {
        this.expertProvider = expertProvider;
        this.expertMapper = expertMapper;
        this.zoneProvider = zoneProvider;
        this.availabilityMapper = availabilityMapper;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public void saveExpert(@Valid ExpertDto expertDto) {
        Expert expert = expertMapper.toEntity(expertDto);
        expertProvider.saveExpert(expert);
    }

    @Override
    public void deleteExpert(Long id) throws BusinessException {
        expertProvider.deleteExpert(id);
    }

    @Override
    public void updateExpert(@Valid ExpertDto expertDto) throws BusinessException {
        Expert existingExpert = expertProvider.getExpertById(expertDto.getId());
        if (existingExpert == null) {
            throw new BusinessException(ApiErrors.EXPERT_NOT_FOUND);
        }

        existingExpert.setFirstName(expertDto.getFirstName());
        existingExpert.setLastName(expertDto.getLastName());
        existingExpert.setZones(expertDto.getZones().stream()
                .map(zoneDto -> {
                    try {
                        return zoneProvider.getZoneById(zoneDto.getId());
                    } catch (BusinessException e) {
                        return null;
                    }
                })
                .filter(z -> z != null)
                .collect(Collectors.toList())
        );
        existingExpert.setAvailabilities(expertDto.getAvailabilities().stream()
                .map(availabilityDto -> availabilityMapper.toEntity(availabilityDto))
                .collect(Collectors.toList())
        );
        existingExpert.setBookings(expertDto.getBookings().stream()
                .map(bookingDto -> bookingMapper.toEntity(bookingDto))
                .collect(Collectors.toList())
        );

        expertProvider.updateExpert(existingExpert);
    }

    @Override
    public ExpertDto getExpertById(Long id) throws BusinessException {
        Expert expert = expertProvider.getExpertById(id);
        if (expert == null) {
            throw new BusinessException(ApiErrors.EXPERT_NOT_FOUND);
        }
        return expertMapper.toDto(expert);
    }

    @Override
    public List<ExpertDto> getAllExperts() throws BusinessException {
        List<Expert> experts = expertProvider.getAllExperts();
        return experts.stream()
                .map(expertMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateAvailabilityForExpert(Long expertId, AvailabilityDto availabilityDto) throws BusinessException {
        Expert expert = expertProvider.getExpertById(expertId);
        if (expert == null) {
            throw new BusinessException(ApiErrors.EXPERT_NOT_FOUND);
        }
        Availability availability = availabilityMapper.toEntity(availabilityDto);
        expert.getAvailabilities().add(availability);
        expertProvider.updateExpert(expert);
    }

    @Override
    public void updateBookingForExpert(Long expertId, BookingDto bookingDto) throws BusinessException {
        Expert expert = expertProvider.getExpertById(expertId);
        if (expert == null) {
            throw new BusinessException(ApiErrors.EXPERT_NOT_FOUND);
        }
        Booking booking = bookingMapper.toEntity(bookingDto);
        expert.getBookings().add(booking);
        expertProvider.updateExpert(expert);
    }

    @Override
    public ExpertDto assignZonesToExpert(Long expertId, List<Long> zoneIds) throws BusinessException {
        Expert expert = expertProvider.getExpertById(expertId);
        if (expert == null) {
            throw new BusinessException(ApiErrors.EXPERT_NOT_FOUND);
        }

        List<Zone> existingZones = expert.getZones();
        List<Zone> newZones = zoneIds.stream()
                .map(zoneId -> {
                    try {
                        return zoneProvider.getZoneById(zoneId);
                    } catch (BusinessException e) {
                        return null;
                    }
                })
                .filter(z -> z != null)
                .collect(Collectors.toList());

        Set<Zone> updatedZones = new HashSet<>(existingZones);
        updatedZones.addAll(newZones);

        expert.setZones(new ArrayList<>(updatedZones));
        expertProvider.updateExpert(expert);

        return expertMapper.toDto(expert);
    }
}
