package com.example.CanchaSystem.repository;

import com.example.CanchaSystem.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface ReservationRespository extends JpaRepository<Reservation,Long> {
    boolean existsById(Long id);
    boolean existsBymatchDate(Date date);

}
