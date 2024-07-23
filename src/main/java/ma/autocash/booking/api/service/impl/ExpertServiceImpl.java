package ma.autocash.booking.api.service.impl;
import ma.autocash.booking.api.dto.ExpertDto;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.entity.Booking;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.EntityNotFoundException;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.mapper.ExpertMapper;
import ma.autocash.booking.api.repository.ExpertRepository;
import ma.autocash.booking.api.repository.AvailabilityRepository;
import ma.autocash.booking.api.repository.BookingRepository;
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
    private final BookingRepository bookingRepository;
    private final AvailabilityRepository availabilityRepository;
    public ExpertServiceImpl(ExpertRepository expertRepository, ExpertMapper expertMapper,
                             ZoneRepository zoneRepository, BookingRepository bookingRepository,
                             AvailabilityRepository availabilityRepository) {
        this.expertRepository = expertRepository;
        this.expertMapper = expertMapper;
        this.zoneRepository = zoneRepository;
        this.bookingRepository = bookingRepository;
        this.availabilityRepository = availabilityRepository;
    }
    @Override
    public ExpertDto saveExpert(@Valid ExpertDto expertDto) throws TechnicalException {
        try {
            Expert expertEntity = expertMapper.toEntity(expertDto);
            Expert savedExpert = expertRepository.save(expertEntity);
            return expertMapper.toDto(savedExpert);
        } catch (Exception e) {
            throw new TechnicalException("Failed to save expert", e);
        }
    }
    @Override
    public ExpertDto updateExpert(Long id, @Valid ExpertDto expertDto) throws TechnicalException {
        try {
            Expert existingExpert = expertRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Expert", id));
            existingExpert.setFirstName(expertDto.getFirstName());
            existingExpert.setLastName(expertDto.getLastName());
            // Update related entities
            updateExpertZones(existingExpert, expertDto.getZoneIds());
            updateExpertBookings(existingExpert, expertDto.getBookingIds());
            updateExpertAvailabilities(existingExpert, expertDto.getAvailabilityIds());
            // Save updated expert
            Expert updatedExpert = expertRepository.save(existingExpert);
            return expertMapper.toDto(updatedExpert);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException("Error updating expert", e);
        }
    }
    private void updateExpertZones(Expert expert, List<Long> zoneIds) {
        if (zoneIds != null) {
            List<Zone> zones = zoneRepository.findAllById(zoneIds);
            expert.setZones(zones);
        } else {
            expert.setZones(null);
        }
    }
    private void updateExpertBookings(Expert expert, List<Long> bookingIds) {
        if (bookingIds != null) {
            List<Booking> bookings = bookingRepository.findAllById(bookingIds);
            expert.setBookings(bookings);
        } else {
            expert.setBookings(null);
        }
    }
    private void updateExpertAvailabilities(Expert expert, List<Long> availabilityIds) {
        if (availabilityIds != null) {
            List<Availability> availabilities = availabilityRepository.findAllById(availabilityIds);
            expert.setAvailabilities(availabilities);
        } else {
            expert.setAvailabilities(null);
        }
    }
    @Override
    public void deleteExpert(Long id) throws TechnicalException {
        try {
            expertRepository.deleteById(id);
        } catch (Exception e) {
            throw new TechnicalException("Failed to delete expert", e);
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
    public ExpertDto getExpertById(@NotNull Long id) {
        Expert expert = expertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expert", id));

        return expertMapper.toDto(expert);
    }
    @Override
    public ExpertDto assignZonesToExpert(Long expertId, List<@NotNull Long> zoneIds) throws EntityNotFoundException, TechnicalException {
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
}
