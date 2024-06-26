package com.AutocashApplication.rdv_expert.repository;
import com.AutocashApplication.rdv_expert.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;


    public interface ExpertRepository extends JpaRepository<Expert, Long> {
    }
