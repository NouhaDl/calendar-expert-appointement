package ma.autocash.booking.api.service.impl;

import ma.autocash.booking.api.dto.*;
import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.ApiErrors;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.mapper.AvailabilityMapper;
import ma.autocash.booking.api.mapper.BookingMapper;
import ma.autocash.booking.api.mapper.ExpertMapper;
import ma.autocash.booking.api.mapper.ZoneMapper;
import ma.autocash.booking.api.provider.ExpertProvider;
import ma.autocash.booking.api.provider.ZoneProvider;
import ma.autocash.booking.api.service.ExpertService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExpertServiceImpl implements ExpertService {

    private final ExpertProvider expertProvider;
    private final ExpertMapper expertMapper;
    private final ZoneProvider zoneProvider;
    private final AvailabilityMapper availabilityMapper;
    private final BookingMapper bookingMapper;
    private final ZoneMapper zoneMapper;

    public ExpertServiceImpl(ExpertProvider expertProvider, ExpertMapper expertMapper, ZoneProvider zoneProvider,
                             AvailabilityMapper availabilityMapper, BookingMapper bookingMapper, ZoneMapper zoneMapper) {
        this.expertProvider = expertProvider;
        this.expertMapper = expertMapper;
        this.zoneProvider = zoneProvider;
        this.availabilityMapper = availabilityMapper;
        this.bookingMapper = bookingMapper;
        this.zoneMapper = zoneMapper;
    }

    @Override
    public void saveExpert( ExpertDto expertDto) {
        Expert expert = expertMapper.toEntity(expertDto);

        // Gérer les zones, disponibilités, et réservations si elles sont présentes
        if (expertDto.getZones() != null) {
            List<Zone> zones = expertDto.getZones().stream()
                    .map(zoneMapper::toEntity)
                    .collect(Collectors.toList());
            expert.setZones(zones);
        }

        if (expertDto.getAvailabilities() != null) {
            List<Availability> availabilities = expertDto.getAvailabilities().stream()
                    .map(availabilityMapper::toResponseEntity)
                    .collect(Collectors.toList());
            expert.setAvailabilities(availabilities);
        }

        if (expertDto.getBookings() != null) {
            List<Booking> bookings = expertDto.getBookings().stream()
                    .map(bookingMapper::toResponseEntity)
                    .collect(Collectors.toList());
            expert.setBookings(bookings);
        }

        expertProvider.saveExpert(expert);
    }


    @Override
    public void deleteExpert(Long id)  {
        expertProvider.deleteExpert(id);
    }

    @Override
    public void updateExpert( ExpertDto expertDto) throws BusinessException {
        Expert existingExpert = expertProvider.getExpertById(expertDto.getId());
        if (existingExpert == null) {
            throw new BusinessException(ApiErrors.EXPERT_NOT_FOUND);
        }

        existingExpert.setFirstName(expertDto.getFirstName());
        existingExpert.setLastName(expertDto.getLastName());

        // Update Zones
        List<Zone> updatedZones = expertDto.getZones().stream()
                .map(zoneMapper::toEntity)
                .collect(Collectors.toList());
        existingExpert.setZones(updatedZones);

        // Update Availabilities
        List<Availability> updatedAvailabilities = expertDto.getAvailabilities().stream()
                .map(availabilityMapper::toResponseEntity)
                .collect(Collectors.toList());
        existingExpert.setAvailabilities(updatedAvailabilities);

        // Update Bookings
        List<Booking> updatedBookings = expertDto.getBookings().stream()
                .map(bookingMapper::toResponseEntity)
                .collect(Collectors.toList());
        existingExpert.setBookings(updatedBookings);

        expertProvider.updateExpert(existingExpert);
    }

    @Override
    public ExpertDto getExpertById(Long id) throws BusinessException {
        Expert expert = expertProvider.getExpertById(id);
        if (expert == null) {
            throw new BusinessException(ApiErrors.EXPERT_NOT_FOUND);
        }
        ExpertDto expertDto = expertMapper.toDto(expert);

        // Set lists of IDs for availability and booking
        expertDto.setAvailabilities(expert.getAvailabilities().stream()
                .map(availabilityMapper::toResponseDto)
                .collect(Collectors.toList()));
        expertDto.setBookings(expert.getBookings().stream()
                .map(bookingMapper::toResponseDto)
                .collect(Collectors.toList()));
        expertDto.setZones(expert.getZones().stream()
                .map(zoneMapper::toDto)
                .collect(Collectors.toList()));

        return expertDto;
    }

    @Override
    public List<ExpertDto> getAllExperts() throws BusinessException {
        List<Expert> experts = expertProvider.getAllExperts();
        return experts.stream()
                .map(expert -> {
                    ExpertDto expertDto = expertMapper.toDto(expert);
                    expertDto.setAvailabilities(expert.getAvailabilities().stream()
                            .map(availabilityMapper::toResponseDto)
                            .collect(Collectors.toList()));
                    expertDto.setBookings(expert.getBookings().stream()
                            .map(bookingMapper::toResponseDto)
                            .collect(Collectors.toList()));
                    expertDto.setZones(expert.getZones().stream()
                            .map(zoneMapper::toDto)
                            .collect(Collectors.toList()));
                    return expertDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void updateAvailabilityForExpert(Long expertId, AvailabilityResponseDto availabilityResponseDto) throws BusinessException {
        Expert expert = expertProvider.getExpertById(expertId);
        if (expert == null) {
            throw new BusinessException(ApiErrors.EXPERT_NOT_FOUND);
        }

        Availability availability = availabilityMapper.toResponseEntity(availabilityResponseDto);
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
        // Récupérer l'expert existant
        Expert expert = expertProvider.getExpertById(expertId);
        if (expert == null) {
            throw new BusinessException(ApiErrors.EXPERT_NOT_FOUND);
        }

        // Récupérer les zones existantes de l'expert
        List<Zone> existingZones = expert.getZones();

        // Récupérer les nouvelles zones basées sur les IDs
        List<Zone> newZones = zoneIds.stream()
                .map(zoneId -> {
                    try {
                        return zoneProvider.getZoneById(zoneId);
                    } catch (BusinessException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // Ajouter les nouvelles zones aux zones existantes, en évitant les doublons
        List<Zone> updatedZones = new ArrayList<>(existingZones);
        for (Zone newZone : newZones) {
            if (!updatedZones.contains(newZone)) {
                updatedZones.add(newZone);
            }
        }

        // Mettre à jour les zones de l'expert
        expert.setZones(updatedZones);
        expertProvider.updateExpert(expert);

        // Convertir l'expert mis à jour en DTO
        ExpertDto expertDto = expertMapper.toDto(expert);
        expertDto.setZones(updatedZones.stream()
                .map(zoneMapper::toDto)
                .collect(Collectors.toList()));

        return expertDto;
    }

}
