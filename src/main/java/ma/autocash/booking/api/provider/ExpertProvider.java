package ma.autocash.booking.api.provider;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.TechnicalException;
import java.util.List;
public interface ExpertProvider {
    Expert saveExpert(Expert expert) throws TechnicalException, BusinessException;
    Expert updateExpert(Expert expert) throws BusinessException, TechnicalException;
    void deleteExpert(Long id) throws BusinessException, TechnicalException;
    List<Expert> getAllExperts() throws TechnicalException, BusinessException;
    Expert getExpertById(Long id) throws BusinessException, TechnicalException;
    Expert assignZonesToExpert(Long expertId, List<Long> zoneIds) throws BusinessException, TechnicalException;
}
