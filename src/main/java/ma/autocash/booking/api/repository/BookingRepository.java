package com.AutocashApplication.rdv_expert.repository;
import com.AutocashApplication.rdv_expert.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
