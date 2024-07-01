package com.autocash.autocashapi.Dtos;
import java.time.LocalDateTime;
public class ReservationDto {
    private Long id;
    private LocalDateTime reservationTime;
    private ExpertDto expert;
// Constructors, getters, setters
public Long getId() {
    return id;
}

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    public ExpertDto getExpert() {
        return expert;
    }

    public void setExpert(ExpertDto expert) {
        this.expert = expert;
    }
}
