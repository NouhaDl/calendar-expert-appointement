package com.AutocashApplication.rdv_expert.service;

import com.AutocashApplication.rdv_expert.entity.Expert;
import com.AutocashApplication.rdv_expert.entity.Zone;
import com.AutocashApplication.rdv_expert.repository.ExpertRepository;
import com.AutocashApplication.rdv_expert.repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpertServiceImpl implements ExpertService {

    private final ExpertRepository expertRepository;
    private final ZoneRepository zoneRepository;

    public ExpertServiceImpl(ExpertRepository expertRepository, ZoneRepository zoneRepository) {
        this.expertRepository = expertRepository;
        this.zoneRepository = zoneRepository;
    }

    @Override
    public Expert saveExpert(Expert expert) {
        return expertRepository.save(expert);
    }

    @Override
    public Expert updateExpert(Expert expert) {
        return expertRepository.save(expert);
    }

    @Override
    public void deleteExpert(Long id) {
        expertRepository.deleteById(id);
    }

    @Override
    public List<Expert> getAllExperts() {
        return expertRepository.findAll();
    }

    @Override
    public Expert getExpertById(Long id) {
        return expertRepository.findById(id).orElse(null);
    }

    @Override
    public Expert assignZoneToExpert(Long expertId, Long zoneId) {
        Optional<Expert> expertOptional = expertRepository.findById(expertId);
        Optional<Zone> zoneOptional = zoneRepository.findById(zoneId);

        if (expertOptional.isPresent() && zoneOptional.isPresent()) {
            Expert expert = expertOptional.get();
            Zone zone = zoneOptional.get();
            expert.getZones().add(zone);
            return expertRepository.save(expert);
        }
        return null;
    }
}
