package ma.autocash.booking.api.provider.impl;

import lombok.SneakyThrows;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.KeyValueErrorImpl;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.provider.ExpertProvider;
import ma.autocash.booking.api.repository.ExpertRepository;
import ma.autocash.booking.api.repository.ZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExpertProviderImpl implements ExpertProvider {

    private final ExpertRepository expertRepository;
    private final ZoneRepository zoneRepository;

    public ExpertProviderImpl(ExpertRepository expertRepository, ZoneRepository zoneRepository) {
        this.expertRepository = expertRepository;
        this.zoneRepository = zoneRepository;
    }

    @SneakyThrows
    @Override
    public Expert saveExpert(Expert expert) throws TechnicalException {
        try {
            return expertRepository.save(expert);
        } catch (Exception e) {
            throw new TechnicalException("Error saving expert", e);
        }
    }

    @SneakyThrows
    @Override
    public Expert updateExpert(Expert expert) throws BusinessException, TechnicalException {
        try {
            if (expertRepository.existsById(expert.getId())) {
                return expertRepository.save(expert);
            } else {
                throw new BusinessException(new KeyValueErrorImpl("expert.update.notfound", 404, 404));
            }
        } catch (Exception e) {
            throw new TechnicalException("Error updating expert", e);
        }
    }

    @SneakyThrows
    @Override
    public void deleteExpert(Long id) throws BusinessException, TechnicalException {
        try {
            if (expertRepository.existsById(id)) {
                expertRepository.deleteById(id);
            } else {
                throw new BusinessException(new KeyValueErrorImpl("expert.delete.notfound", 404, 404));
            }
        } catch (Exception e) {
            throw new TechnicalException("Error deleting expert", e);
        }
    }

    @SneakyThrows
    @Override
    public List<Expert> getAllExperts() throws TechnicalException {
        try {
            return expertRepository.findAll();
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving experts", e);
        }
    }

    @SneakyThrows
    @Override
    public Expert getExpertById(Long id) throws BusinessException, TechnicalException {
        try {
            return expertRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("expert.get.notfound", 404, 404)));
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving expert by id", e);
        }
    }

    @SneakyThrows
    @Override
    public Expert assignZoneToExpert(Long expertId, Long zoneId) throws BusinessException, TechnicalException {
        try {
            Optional<Expert> expertOptional = expertRepository.findById(expertId);
            Optional<Zone> zoneOptional = zoneRepository.findById(zoneId);

            if (expertOptional.isPresent() && zoneOptional.isPresent()) {
                Expert expert = expertOptional.get();
                Zone zone = zoneOptional.get();
                expert.getZones().add(zone);
                return expertRepository.save(expert);
            } else {
                throw new BusinessException(new KeyValueErrorImpl("expert.or.zone.notfound", 404, 404));
            }
        } catch (Exception e) {
            throw new TechnicalException("Error assigning zone to expert", e);
        }
    }
}
