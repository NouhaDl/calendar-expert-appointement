package ma.autocash.booking.api.provider.impl;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.KeyValueErrorImpl;
import ma.autocash.booking.api.provider.ExpertProvider;
import ma.autocash.booking.api.repository.ExpertRepository;
import ma.autocash.booking.api.repository.ZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExpertProviderImpl implements ExpertProvider {

    private final ExpertRepository expertRepository;
    private final ZoneRepository zoneRepository;
    // TODO: Please check all exceptions and only keep necessary ones
    public ExpertProviderImpl(ExpertRepository expertRepository, ZoneRepository zoneRepository) {
        this.expertRepository = expertRepository;
        this.zoneRepository = zoneRepository;
    }

    @Override
    public Expert saveExpert(Expert expert) throws BusinessException {
        try {
            return expertRepository.save(expert);
        } catch (Exception e) {
            throw new BusinessException(new KeyValueErrorImpl("expert.cannot.be saved", 404, 404));
        }
    }

    @Override
    public Expert updateExpert(Expert expert) throws BusinessException {
        try {
            if (expertRepository.existsById(expert.getId())) {
                return expertRepository.save(expert);
            } else {
                throw new BusinessException(new KeyValueErrorImpl("expert.update.notfound", 404, 404));
            }
        } catch (Exception e) {
            throw new BusinessException(new KeyValueErrorImpl("expert.cannot.be updated", 404, 404));
        }
    }
    @Override
    public void deleteExpert(Long id) throws BusinessException {
        try {
            if (expertRepository.existsById(id)) {
                expertRepository.deleteById(id);
            } else {
                throw new BusinessException(new KeyValueErrorImpl("expert.delete.notfound", 404, 404));
            }
        } catch (Exception e) {
            throw new BusinessException(new KeyValueErrorImpl("expert.delete.notfound", 404, 404));
        }
    }
    @Override
    public List<Expert> getAllExperts() throws BusinessException {
        try {
            return expertRepository.findAll();
        } catch (Exception e) {
            throw new BusinessException(new KeyValueErrorImpl("expert.get.notfound", 404, 404));
        }
    }
    @Override
    public Expert getExpertById(Long id) throws BusinessException {
        try {
            return expertRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("expert.get.notfound", 404, 404)));
        } catch (Exception e) {
            throw new BusinessException(new KeyValueErrorImpl("expert.get.notfound", 404, 404));
        }
    }
    @Override
    public Expert assignZonesToExpert(Long expertId, List<Long> zoneIds) throws BusinessException {
        try {
            Optional<Expert> expertOptional = expertRepository.findById(expertId);

            if (expertOptional.isPresent()) {
                Expert expert = expertOptional.get();
                List<Zone> zones = zoneIds.stream()
                        .map(zoneId -> {
                            try {
                                return zoneRepository.findById(zoneId)
                                        .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("zone.notfound", 404, 404)));
                            } catch (BusinessException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .collect(Collectors.toList());
                expert.setZones(zones);
                return expertRepository.save(expert);
            } else {
                throw new BusinessException(new KeyValueErrorImpl("expert.notfound", 404, 404));
            }
        } catch (Exception e) {
            throw new BusinessException(new KeyValueErrorImpl("expert.cannot.be assigned zones", 404, 404));
        }
    }
}
