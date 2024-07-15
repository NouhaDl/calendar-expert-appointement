package ma.autocash.booking.api.service.impl;

import ma.autocash.booking.api.dto.ExpertDto;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.EntityNotFoundException;
import ma.autocash.booking.api.exception.KeyValueErrorImpl;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.mapper.ExpertMapper;
import ma.autocash.booking.api.repository.ExpertRepository;
import ma.autocash.booking.api.repository.ZoneRepository;
import ma.autocash.booking.api.service.ExpertService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExpertServiceImpl implements ExpertService {

    private final ExpertRepository expertRepository;
    private final ExpertMapper expertMapper;
    private final ZoneRepository zoneRepository;

    public ExpertServiceImpl(ExpertRepository expertRepository, ExpertMapper expertMapper, ZoneRepository zoneRepository) {
        this.expertRepository = expertRepository;
        this.expertMapper = expertMapper;
        this.zoneRepository = zoneRepository;
    }

    @Override
    public ExpertDto saveExpert(@Valid ExpertDto expertDto) throws TechnicalException {
        try {
            Expert expertEntity = expertMapper.toEntity(expertDto);
            Expert savedExpert = expertRepository.save(expertEntity);
            return expertMapper.toDto(savedExpert);
        } catch (Exception e) {
            throw new TechnicalException(new KeyValueErrorImpl("expert.cannot.be saved", 404, 404));
        }
    }

    @Override
    public ExpertDto updateExpert(Long id, @Valid ExpertDto expertDto) throws TechnicalException {
        try {
            Expert existingExpert = expertRepository.findById(id)
                    .orElseThrow(() -> new TechnicalException(new KeyValueErrorImpl("expert.cannot.be updated", 404, 404)));


            existingExpert.setFirstName(expertDto.getFirstName());
            existingExpert.setLastName(expertDto.getLastName());
            updateExpertZones(existingExpert, expertDto.getZoneIds());
            updateExpertBookings(existingExpert, expertDto.getBookingIds());
            updateExpertAvailabilities(existingExpert, expertDto.getAvailabilityIds());

            Expert updatedExpert = expertRepository.save(existingExpert);
            return expertMapper.toDto(updatedExpert);
        } catch (Exception e) {
            throw new TechnicalException("Error updating expert", e);
        }
    }

    @Override
    public void deleteExpert(Long id) throws TechnicalException {
        try {
            expertRepository.deleteById(id);
        } catch (Exception e) {
            throw new TechnicalException(new KeyValueErrorImpl("expert.cannot.be deleted", 404, 404));
        }
    }

    @Override
    public List<ExpertDto> getAllExperts() {
        List<Expert> experts = expertRepository.findAll();
        return experts.stream()
                .map(expertMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ExpertDto getExpertById(Long id) {
        Expert expert = expertRepository.findById(id)
                .orElseThrow(() ->new EntityNotFoundException("Expert",id));

        return expertMapper.toDto(expert);
    }

    @Override
    public ExpertDto assignZonesToExpert(Long expertId, List<Long> zoneIds) throws EntityNotFoundException, TechnicalException {
        try {
            Expert expert = expertRepository.findById(expertId)
                    .orElseThrow(() -> new EntityNotFoundException("Expert", expertId));

            List<Zone> zones = zoneRepository.findAllById(zoneIds);
            expert.getZones().addAll(zones);

            Expert updatedExpert = expertRepository.save(expert);
            return expertMapper.toDto(updatedExpert);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException("Failed to assign zones to expert", e);
        }
    }


    private void updateExpertZones(@NotNull Expert expert, List<Long> zoneIds) {
        if (zoneIds != null) {
            List<Zone> zones = zoneRepository.findAllById(zoneIds);
            expert.setZones(zones);
        } else {
            expert.setZones(null);
        }
    }

    private void updateExpertBookings(@NotNull Expert expert, List<Long> bookingIds) {
        if (bookingIds != null) {
            List<Booking> bookings = bookingIds.stream()
                    .map(bookingId -> {
                        Booking booking = expert.getBookings().stream()
                                .filter(b -> b.getId().equals(bookingId))
                                .findFirst()
                                .orElseGet(Booking::new);

                        return booking;
                    })
                    .toList();
            expert.setBookings(bookings);
        } else {
            expert.setBookings(null);
        }
    }

    private void updateExpertAvailabilities(@NotNull Expert expert, List<Long> availabilityIds) {
        if (availabilityIds != null) {
            List<Availability> availabilities = availabilityIds.stream()
                    .map(availabilityId -> {
                        Availability availability = expert.getAvailabilities().stream()
                                .filter(a -> a.getId().equals(availabilityId))
                                .findFirst()
                                .orElseGet(Availability::new);

                        return availability;
                    })
                    .toList();
            expert.setAvailabilities(availabilities);
        } else {
            expert.setAvailabilities(null);
        }
    }
}
