package ma.autocash.booking.api.service;

import ma.autocash.booking.api.dto.ExpertDto;
import ma.autocash.booking.api.exception.TechnicalException;

import java.util.List;

public interface ExpertService {

    ExpertDto saveExpert(ExpertDto expertDto) throws TechnicalException;

    ExpertDto updateExpert(Long id, ExpertDto expertDto) throws TechnicalException;

    void deleteExpert(Long id) throws TechnicalException;

    List<ExpertDto> getAllExperts();

    ExpertDto getExpertById(Long id);

    ExpertDto assignZonesToExpert(Long expertId, List<Long> zoneIds) throws TechnicalException;
}