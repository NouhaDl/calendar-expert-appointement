package ma.autocash.booking.api.service;

import ma.autocash.booking.api.dto.ExpertDto;
import ma.autocash.booking.api.exception.BusinessException;

import java.util.List;

public interface ExpertService {

    ExpertDto saveExpert(ExpertDto expertDto) throws BusinessException;

    ExpertDto updateExpert(Long id, ExpertDto expertDto) throws BusinessException;

    void deleteExpert(Long id) ;

    List<ExpertDto> getAllExperts() throws BusinessException;

    ExpertDto getExpertById(Long id) throws BusinessException;

    ExpertDto assignZonesToExpert(Long expertId, List<Long> zoneIds) throws BusinessException;
}
