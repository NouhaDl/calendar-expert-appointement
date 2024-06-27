package ma.autocash.booking.api.provider;

import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.repository.ExpertRepository;
import ma.autocash.booking.api.repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpertProviderImpl implements ExpertProvider {

    private final ExpertRepository expertRepository;
    private final ZoneRepository zoneRepository;

    public ExpertProviderImpl(ExpertRepository expertRepository, ZoneRepository zoneRepository) {
        this.expertRepository = expertRepository;
        this.zoneRepository = zoneRepository;
    }

    @Override
    public Expert saveExpert(Expert expert) {
        try {
            return expertRepository.save(expert);
        } catch (Exception e) {
            throw new TechnicalException("Error saving expert", e);
        }
    }

    @Override
    public Expert updateExpert(Expert expert) {
        try {
            if (expertRepository.existsById(expert.getId())) {
                return expertRepository.save(expert);
            } else {
                throw new BusinessException("Expert not found for update");
            }
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
    public List<Expert> getAllExperts() {
        try {
            return expertRepository.findAll();
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving experts", e);
        }
    }

    @Override
    public Expert getExpertById(Long id) {
        try {
            return expertRepository.findById(id).orElseThrow(() -> new BusinessException("Expert not found"));
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving expert by id", e);
        }
    }

    @Override
    public Expert assignZoneToExpert(Long expertId, Long zoneId) {
        try {
            Optional<Expert> expertOptional = expertRepository.findById(expertId);
            Optional<Zone> zoneOptional = zoneRepository.findById(zoneId);

            if (expertOptional.isPresent() && zoneOptional.isPresent()) {
                Expert expert = expertOptional.get();
                Zone zone = zoneOptional.get();
                expert.getZones().add(zone);
                return expertRepository.save(expert);
            } else {
                throw new BusinessException("Expert or Zone not found for assignment");
            }
        } catch (Exception e) {
            throw new TechnicalException("Error assigning zone to expert", e);
        }
    }
}
