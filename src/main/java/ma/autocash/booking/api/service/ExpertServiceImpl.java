package ma.autocash.booking.api.service;

import ma.autocash.booking.api.dto.ExpertDto;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.mapper.ExpertMapper;
import ma.autocash.booking.api.repository.ExpertRepository;
import ma.autocash.booking.api.repository.ZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExpertServiceImpl implements ExpertService {

    private final ExpertRepository expertRepository;
    private final ZoneRepository zoneRepository;
    private final ExpertMapper expertMapper;

    public ExpertServiceImpl(ExpertRepository expertRepository, ZoneRepository zoneRepository, ExpertMapper expertMapper) {
        this.expertRepository = expertRepository;
        this.zoneRepository = zoneRepository;
        this.expertMapper = expertMapper;
    }

    @Override
    public ExpertDto saveExpert(ExpertDto expertDto) {
        try {
            Expert expert = expertMapper.toEntity(expertDto, loadZonesByIds(expertDto.getZoneIds()));
            Expert savedExpert = expertRepository.save(expert);
            return expertMapper.toDTO(savedExpert);
        } catch (Exception e) {
            throw new TechnicalException("Error saving expert", e);
        }
    }

    @Override
    public ExpertDto updateExpert(Long id, ExpertDto expertDto) {
        try {
            Expert existingExpert = expertRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Expert not found for update"));

            expertMapper.updateFromDto(expertDto, existingExpert, loadZonesByIds(expertDto.getZoneIds()));

            expertRepository.save(existingExpert);

            return expertMapper.toDTO(existingExpert);
        } catch (Exception e) {
            throw new TechnicalException("Error updating expert", e);
        }
    }

    @Override
    public void deleteExpert(Long id) {
        try {
            if (expertRepository.existsById(id)) {
                expertRepository.deleteById(id);
            } else {
                throw new BusinessException("Expert not found for deletion");
            }
        } catch (Exception e) {
            throw new TechnicalException("Error deleting expert", e);
        }
    }

    @Override
    public List<ExpertDto> getAllExperts() {
        try {
            List<Expert> experts = expertRepository.findAll();
            return experts.stream()
                    .map(expertMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving experts", e);
        }
    }

    @Override
    public ExpertDto getExpertById(Long id) {
        try {
            Expert expert = expertRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Expert not found"));
            return expertMapper.toDTO(expert);
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving expert by id", e);
        }
    }

    @Override
    public ExpertDto assignZoneToExpert(Long expertId, Long zoneId) {
        try {
            Expert expert = expertRepository.findById(expertId)
                    .orElseThrow(() -> new BusinessException("Expert not found"));

            Zone zone = zoneRepository.findById(zoneId)
                    .orElseThrow(() -> new BusinessException("Zone not found"));

            expert.getZones().add(zone);

            Expert updatedExpert = expertRepository.save(expert);

            return expertMapper.toDTO(updatedExpert);
        } catch (Exception e) {
            throw new TechnicalException("Error assigning zone to expert", e);
        }
    }

    private List<Zone> loadZonesByIds(List<Long> zoneIds) {
        return zoneIds.stream()
                .map(zoneId -> zoneRepository.findById(zoneId)
                        .orElseThrow(() -> new BusinessException("Zone not found with id: " + zoneId)))
                .collect(Collectors.toList());
    }
}
