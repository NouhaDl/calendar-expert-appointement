package ma.autocash.booking.api.services.impl;

import ma.autocash.booking.api.dto.ExpertDto;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.mapper.ExpertMapper;
import ma.autocash.booking.api.repository.ExpertRepository;
import ma.autocash.booking.api.repository.ZoneRepository;
import ma.autocash.booking.api.services.ExpertService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ExpertDto saveExpert(ExpertDto expertDto) {
        Expert expert = expertMapper.toEntity(expertDto);
        Expert savedExpert = expertRepository.save(expert);
        ExpertDto savedExpertDto = expertMapper.toDto(savedExpert);
        savedExpertDto.setId(savedExpert.getId());
        return savedExpertDto;
    }

    @Override
    public ExpertDto updateExpert(Long id, ExpertDto expertDto) throws TechnicalException {
        try {
            Expert existingExpert = expertRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Expert not found with id: " + id));

            existingExpert.setFirstName(expertDto.getFirstName());
            existingExpert.setLastName(expertDto.getLastName());
            existingExpert.setZoneIds(expertDto.getZoneIds());
            existingExpert.setBookingIds(expertDto.getBookingIds());
            existingExpert.setAvailabilityIds(expertDto.getAvailabilityIds());

            Expert updatedExpert = expertRepository.save(existingExpert);
            return expertMapper.toDto(updatedExpert);
        } catch (Exception e) {
            throw new TechnicalException("Error updating expert", e);
        }
    }

    @Override
    public void deleteExpert(Long id) {
        expertRepository.deleteById(id);
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
                .orElseThrow(() -> new RuntimeException("Expert not found with id: " + id));
        return expertMapper.toDto(expert);
    }

    @Override
    public ExpertDto assignZonesToExpert(Long expertId, List<Long> zoneIds) {
        Expert expert = expertRepository.findById(expertId)
                .orElseThrow(() -> new RuntimeException("Expert not found with id: " + expertId));

        List<Zone> zones = zoneIds.stream()
                .map(zoneId -> zoneRepository.findById(zoneId)
                        .orElseThrow(() -> new RuntimeException("Zone not found with id: " + zoneId)))
                .toList();

        expert.getZones().addAll(zones);

        Expert updatedExpert = expertRepository.save(expert);

        return expertMapper.toDto(updatedExpert);
    }
}