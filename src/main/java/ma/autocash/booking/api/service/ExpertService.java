package ma.autocash.booking.api.service;

import ma.autocash.booking.api.dto.ExpertDto;
import ma.autocash.booking.api.entity.Expert;

import java.util.List;

public interface ExpertService {

    ExpertDto saveExpert(ExpertDto expertDto);

    ExpertDto updateExpert(Long id, ExpertDto expertDto);

    void deleteExpert(Long id);

    List<ExpertDto> getAllExperts();

    ExpertDto getExpertById(Long id);

    ExpertDto assignZoneToExpert(Long expertId, Long zoneId);
}
