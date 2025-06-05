package com.example.CanchaSystem.repository;

import com.example.CanchaSystem.model.Reservation;
import com.example.CanchaSystem.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRespository extends JpaRepository<Reservation,Long> {
    boolean existsById(Long id);
    boolean existsBymatchDate(LocalDateTime date);

    List<Reservation> findByCanchaId(Long canchaId);
    List<Reservation> findByClientId(Long clientId);
    List<Reservation> findByCanchaIdAndMatchDateBetween(Long canchaId, LocalDateTime from, LocalDateTime until);

    List<Reservation> findByMatchDateBetween(LocalDateTime from, LocalDateTime until);
    List<Reservation> findByMatchDateBeforeAndStatus(LocalDateTime now, ReservationStatus status);
}
