package com.autocash.autocashapi.Services;
import com.autocash.autocashapi.Dtos.ExpertDto;
import com.autocash.autocashapi.Entities.ExpertEntity;
import com.autocash.autocashapi.Entities.ZoneEntity;

import com.autocash.autocashapi.Mappers.ExpertMapper;
import com.autocash.autocashapi.Repositories.ExpertRepository;
import com.autocash.autocashapi.Repositories.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExpertService {
    @Autowired
    private ExpertRepository expertRepository;

    @Autowired
    private ExpertMapper expertMapper;

    public ExpertDto saveExpert(ExpertDto expertDto) {
        ExpertEntity expertEntity = expertMapper.toEntity(expertDto);
        expertEntity = expertRepository.save(expertEntity);
        return expertMapper.toDto(expertEntity);
    }

    public List<ExpertDto> findAllExperts() {
        List<ExpertEntity> expertEntities = expertRepository.findAll();
        return expertEntities.stream()
                .map(expertMapper::toDto)
                .collect(Collectors.toList());
    }

    public ExpertDto findExpertById(Long id) {
        ExpertEntity expertEntity = expertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expert not found with id: " + id));
        return expertMapper.toDto(expertEntity);
    }

    public ExpertDto updateExpert(Long id, ExpertDto expertDto) {
        ExpertEntity existingExpertEntity = expertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expert not found with id: " + id));

        // Update existingExpertEntity with new data from expertDto
        existingExpertEntity.setName(expertDto.getName());

        ExpertEntity updatedExpertEntity = expertRepository.save(existingExpertEntity);
        return expertMapper.toDto(updatedExpertEntity);
    }

    public boolean deleteExpert(Long id) {
        if (expertRepository.existsById(id)) {
            expertRepository.deleteById(id);
            return true;
        }
        return false;
    }
    @Autowired
    private ZoneRepository zoneRepository;

    public ExpertDto assignExpertToZone(Long expertId, Long zoneId) {
        ExpertEntity expertEntity = expertRepository.findById(expertId)
                .orElseThrow(() -> new RuntimeException("Expert not found with id: " + expertId));

        ZoneEntity zoneEntity = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new RuntimeException("Zone not found with id: " + zoneId));

        expertEntity.setZone(zoneEntity); // Assigne la zone récupérée à l'expert

        ExpertEntity updatedExpertEntity = expertRepository.save(expertEntity);
        return expertMapper.toDto(updatedExpertEntity);
    }
    // Add other service methods as needed
}