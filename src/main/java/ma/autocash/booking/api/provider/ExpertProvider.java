package ma.autocash.booking.api.provider;

import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.exception.BusinessException;

import java.util.List;

public interface ExpertProvider {

    Expert saveExpert(Expert expert) throws BusinessException;

    Expert updateExpert(Expert expert) throws BusinessException;

    void deleteExpert(Long id) throws BusinessException;

    List<Expert> getAllExperts() throws BusinessException;

    Expert getExpertById(Long id) throws BusinessException;

    Expert assignZonesToExpert(Long expertId, List<Long> zoneIds) throws BusinessException;
}
