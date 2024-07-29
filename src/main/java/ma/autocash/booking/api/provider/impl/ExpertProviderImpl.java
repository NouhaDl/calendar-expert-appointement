package ma.autocash.booking.api.provider.impl;

import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.exception.ApiErrors;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.provider.ExpertProvider;
import ma.autocash.booking.api.repository.ExpertRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExpertProviderImpl implements ExpertProvider {

    private final ExpertRepository expertRepository;

    public ExpertProviderImpl(ExpertRepository expertRepository) {
        this.expertRepository = expertRepository;
    }

    @Override
    public Expert saveExpert(Expert expert) {
        return expertRepository.save(expert);
    }

    @Override
    public void updateExpert(Expert expert) throws BusinessException {
        if (expertRepository.existsById(expert.getId())) {
           expertRepository.save(expert);
        } else {
            throw new BusinessException(ApiErrors.EXPERT_NOT_FOUND);
        }
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
        return expertRepository.save(expert);
    }
}
