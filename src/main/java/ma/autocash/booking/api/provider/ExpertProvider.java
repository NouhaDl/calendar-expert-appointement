package ma.autocash.booking.api.provider;

import ma.autocash.booking.api.entity.Expert;


import java.util.List;

public interface ExpertProvider {

    Expert saveExpert(Expert expert);

    Expert updateExpert(Expert expert);

    void deleteExpert(Long id);

    List<Expert> getAllExperts();

    Expert getExpertById(Long id);

    Expert assignZoneToExpert(Long expertId, Long zoneId);
}
