package com.autocash.autocashapi.Repositories;

import com.autocash.autocashapi.Entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
}