package ma.autocash.booking.api.provider;

import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.exception.BusinessException;

import java.util.List;

public interface ExpertProvider {

    void  saveExpert(Expert expert);

    void updateExpert( Expert expert) throws BusinessException;

    void deleteExpert(Long id);

    Expert getExpertById(Long id) throws BusinessException;

    List<Expert> getAllExperts() throws BusinessException;

    Expert assignZonesToExpert(Long expertId, List<Long> zoneIds) throws BusinessException;
}
