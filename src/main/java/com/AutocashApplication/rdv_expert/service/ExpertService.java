package com.AutocashApplication.rdv_expert.service;

import com.AutocashApplication.rdv_expert.entity.Expert;


import java.util.List;

public interface ExpertService {

    Expert saveExpert(Expert expert);

    Expert updateExpert(Expert expert);

    void deleteExpert(Long id);

    List<Expert> getAllExperts();

    Expert getExpertById(Long id);

    Expert assignZoneToExpert(Long expertId, Long zoneId);
}
