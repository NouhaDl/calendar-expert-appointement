package ma.autocash.booking.api.provider.impl;

import jakarta.validation.Valid;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.ApiErrors;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.provider.ExpertProvider;
import ma.autocash.booking.api.repository.ExpertRepository;
import ma.autocash.booking.api.repository.ZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ExpertProviderImpl implements ExpertProvider {

    private final ExpertRepository expertRepository;
    private final ZoneRepository zoneRepository;

    public ExpertProviderImpl(ExpertRepository expertRepository, ZoneRepository zoneRepository) {
        this.expertRepository = expertRepository;
        this.zoneRepository = zoneRepository;
    }

    @Override
    public void saveExpert(@Valid Expert expert) {
        expertRepository.save(expert);
    }

    @Override
    public void updateExpert(@Valid Expert expert) throws BusinessException {
        if (expert == null || expert.getId() == null) {
            throw new BusinessException(ApiErrors.EXPERT_NOT_FOUND);
        }

        Expert existingExpert = expertRepository.findById(expert.getId())
                .orElseThrow(() -> new BusinessException(ApiErrors.EXPERT_NOT_FOUND));

        existingExpert.setFirstName(expert.getFirstName());
        existingExpert.setLastName(expert.getLastName());

        List<Zone> updatedZones = new ArrayList<>(existingExpert.getZones());
        for (Zone zone : expert.getZones()) {
            if (!updatedZones.contains(zone)) {
                updatedZones.add(zone);
            }
        }
        existingExpert.setZones(updatedZones);

        expertRepository.save(existingExpert);
    }

    @Override
    public void deleteExpert(Long id) {
        expertRepository.deleteById(id);
    }

    @Override
    public Expert getExpertById(Long id) throws BusinessException {
        return expertRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApiErrors.EXPERT_NOT_FOUND));
    }

    @Override
    public List<Expert> getAllExperts() throws BusinessException {
        List<Expert> experts = expertRepository.findAll();
        if (experts.isEmpty()) {
            throw new BusinessException(ApiErrors.EXPERT_NOT_FOUND);
        }
        return experts;
    }

    @Override
    public Expert assignZonesToExpert(Long expertId, List<Long> zoneIds) throws BusinessException {
        Expert expert = getExpertById(expertId);
        List<Zone> currentZones = expert.getZones();
        List<Zone> newZones = zoneRepository.findAllById(zoneIds);

        for (Zone newZone : newZones) {
            if (!currentZones.contains(newZone)) {
                currentZones.add(newZone);
            }
        }

        expert.setZones(currentZones);
        return expertRepository.save(expert);
    }
}
