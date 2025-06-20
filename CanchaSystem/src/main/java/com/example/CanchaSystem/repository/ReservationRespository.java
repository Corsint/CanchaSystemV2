package com.example.CanchaSystem.repository;

import com.example.CanchaSystem.model.Owner;
import com.example.CanchaSystem.model.Reservation;
import com.example.CanchaSystem.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRespository extends JpaRepository<Reservation,Long> {
    boolean existsById(Long id);
    boolean existsBymatchDateAndCanchaId(LocalDateTime date,Long canchaId);
    Optional<Reservation> findById(Long id);

    List<Reservation> findByCanchaId(Long canchaId);
    List<Reservation> findByClientId(Long clientId);
    List<Reservation> findByCanchaIdAndMatchDateBetween(Long canchaId, LocalDateTime from, LocalDateTime until);

    List<Reservation> findByMatchDateBetweenAndStatus(LocalDateTime from, LocalDateTime until,ReservationStatus status);
    List<Reservation> findByMatchDateBeforeAndStatus(LocalDateTime now, ReservationStatus status);
    List<Reservation> findByCanchaIdAndMatchDateBetweenAndStatus(
            Long canchaId, LocalDateTime start, LocalDateTime end, ReservationStatus status);

    @Query("SELECT r FROM Reservation r " +
            "JOIN r.cancha c " +
            "JOIN c.brand b " +
            "WHERE b.id = :brandId")
    List<Reservation> findAllByBrandId(@Param("brandId") Long brandId);

    @Query("SELECT r FROM Reservation r " +
            "JOIN r.cancha c " +
            "JOIN c.brand b " +
            "JOIN b.owner o " +
            "WHERE o.id = :ownerId")
    List<Reservation> findAllByOwnerId(@Param("ownerId") Long ownerId);
    List<Reservation> findByStatusAndMatchDateBefore(ReservationStatus status, LocalDateTime date);

    @Query("SELECT b.owner FROM Reservation r " +
            "JOIN r.cancha c " +
            "JOIN c.brand b " +
            "WHERE c.id = :canchaId")
    Owner findOwnerByCanchaId(@Param("canchaId") Long canchaId);

}
