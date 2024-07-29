package ma.autocash.booking.api.service.impl;

import ma.autocash.booking.api.dto.ExpertDto;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.exception.ApiErrors;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.mapper.ExpertMapper;
import ma.autocash.booking.api.provider.ExpertProvider;
import ma.autocash.booking.api.service.ExpertService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExpertServiceImpl implements ExpertService {

    private final ExpertProvider expertProvider;
    private final ExpertMapper expertMapper;

    public ExpertServiceImpl(ExpertProvider expertProvider, ExpertMapper expertMapper) {
        this.expertProvider = expertProvider;
        this.expertMapper = expertMapper;
    }

    @Override
    public ExpertDto saveExpert(ExpertDto expertDto) throws BusinessException {
        Expert expert = expertMapper.toEntity(expertDto);
        Expert savedExpert = expertProvider.saveExpert(expert);

        if (expertDto.getZoneIds() != null && !expertDto.getZoneIds().isEmpty()) {
            expertProvider.assignZonesToExpert(savedExpert.getId(), expertDto.getZoneIds());
        }

        return expertMapper.toDto(savedExpert);
    }

    @Override
    public ExpertDto updateExpert(Long id, ExpertDto expertDto) throws BusinessException {
        Expert existingExpert = expertProvider.getExpertById(id);
        if (existingExpert != null) {
            Expert expert = expertMapper.toEntity(expertDto);
            expert.setId(id);
            expertProvider.updateExpert(expert);

            if (expertDto.getZoneIds() != null && !expertDto.getZoneIds().isEmpty()) {
                expertProvider.assignZonesToExpert(id, expertDto.getZoneIds());
            }

            return expertMapper.toDto(expertProvider.getExpertById(id));
        } else {
            throw new BusinessException(ApiErrors.EXPERT_NOT_FOUND);
        }
    }

    @Override
    public void deleteExpert(Long id)  {
            expertProvider.deleteExpert(id);

    }

    @Override
    public List<ExpertDto> getAllExperts() throws BusinessException {
        List<Expert> experts = expertProvider.getAllExperts();
        return expertMapper.toDtoList(experts);
    }

    @Override
    public ExpertDto getExpertById(Long id) throws BusinessException {
        Expert expert = expertProvider.getExpertById(id);
        if (expert != null) {
            return expertMapper.toDto(expert);
        } else {
            throw new BusinessException(ApiErrors.EXPERT_NOT_FOUND);
        }
    }

    @Override
    public ExpertDto assignZonesToExpert(Long expertId, List<Long> zoneIds) throws BusinessException {
        Expert expert = expertProvider.assignZonesToExpert(expertId, zoneIds);
        return expertMapper.toDto(expert);
    }
}
