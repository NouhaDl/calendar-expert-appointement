package com.autocash.autocashapi.Services;
import com.autocash.autocashapi.Dtos.ReservationDto;
import com.autocash.autocashapi.Entities.ReservationEntity;
import com.autocash.autocashapi.Mappers.ReservationMapper;
import com.autocash.autocashapi.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationMapper reservationMapper;

    public ReservationDto saveReservation(ReservationDto reservationDto) {
        ReservationEntity reservationEntity = reservationMapper.toEntity(reservationDto);
        reservationEntity = reservationRepository.save(reservationEntity);
        return reservationMapper.toDto(reservationEntity);
    }

    public List<ReservationDto> findAllReservations() {
        List<ReservationEntity> reservationEntities = reservationRepository.findAll();
        return reservationEntities.stream()
                .map(reservationMapper::toDto)
                .collect(Collectors.toList());
    }

    public ReservationDto findReservationById(Long id) {
        ReservationEntity reservationEntity = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
        return reservationMapper.toDto(reservationEntity);
    }

    public ReservationDto updateReservation(Long id, ReservationDto reservationDto) {
        ReservationEntity existingReservationEntity = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

        // Update existingReservationEntity with new data from reservationDto
        existingReservationEntity.setReservationTime(reservationDto.getReservationTime());
        // Update other fields as needed

        ReservationEntity updatedReservationEntity = reservationRepository.save(existingReservationEntity);
        return reservationMapper.toDto(updatedReservationEntity);
    }

    public boolean deleteReservation(Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Add other service methods as needed
}

