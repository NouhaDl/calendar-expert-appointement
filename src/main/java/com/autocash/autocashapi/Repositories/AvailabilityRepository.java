package com.autocash.autocashapi.Repositories;

import com.autocash.autocashapi.Entities.AvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
public interface AvailabilityRepository extends JpaRepository<AvailabilityEntity, Long> {
    List<AvailabilityEntity> findByExpertIdAndStartTimeBetween(Long expertId, LocalDateTime startTime, LocalDateTime endTime);
    List<AvailabilityEntity> findByExpert_ZoneId(Long zoneId);
}