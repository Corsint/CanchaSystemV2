package com.example.CanchaSystem.repository;

import com.example.CanchaSystem.model.Reservation;
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

    Optional<List<Reservation>> findByCanchaId(Long canchaId);
    Optional<List<Reservation>> findByClientId(Long clientId);
    Optional<List<Reservation>> findByCanchaIdAndMatchDateBetween(Long canchaId, LocalDateTime from, LocalDateTime until);
}
