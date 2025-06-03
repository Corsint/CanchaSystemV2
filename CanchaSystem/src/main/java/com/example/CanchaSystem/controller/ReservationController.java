package com.example.CanchaSystem.controller;

import com.example.CanchaSystem.exception.cancha.CanchaNotFoundException;
import com.example.CanchaSystem.exception.client.ClientNotFoundException;
import com.example.CanchaSystem.exception.client.NoClientsException;
import com.example.CanchaSystem.exception.misc.BankAlreadyLinkedException;
import com.example.CanchaSystem.exception.misc.CellNumberAlreadyAddedException;
import com.example.CanchaSystem.exception.misc.MailAlreadyRegisteredException;
import com.example.CanchaSystem.exception.misc.UsernameAlreadyExistsException;
import com.example.CanchaSystem.exception.reservation.IllegalReservationDateException;
import com.example.CanchaSystem.exception.reservation.NoReservationsException;
import com.example.CanchaSystem.exception.reservation.ReservationNotFoundException;
import com.example.CanchaSystem.model.Client;
import com.example.CanchaSystem.model.Reservation;
import com.example.CanchaSystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/reservation")
@PreAuthorize("hasRole('CLIENT')")

public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/insert")
    public ResponseEntity<?> insertReservation(@Validated @RequestBody Reservation reservation) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.insertReservation(reservation));

        } catch (IllegalReservationDateException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error",e.getMessage()));
        }
    }


    @GetMapping("/findall")
    public List<Reservation> getReservations() {
        try {
            return reservationService.getAllReservations();
        } catch (NoReservationsException e) {
            System.err.println(e.getMessage());
            return null;
        }

    }

    @PutMapping("/update")
    public void updateReservation(@RequestBody Reservation reservation) {
        try {
            reservationService.updateReservation(reservation);
        } catch (ReservationNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        try {
            reservationService.deleteReservation(id);
        } catch (ReservationNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<Reservation> findReservationById(@PathVariable Long id) {
        try {
            return Optional.ofNullable(reservationService.findReservationById(id));
        } catch (ReservationNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }

    @GetMapping("/getAvailableHours/{canchaId}/{day}") // ejemplo: http://localhost:8080/api/reservations/getAvailableHours/1/2025-06-10
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN') or hasRole('CLIENT')")
    public ResponseEntity<List<LocalTime>> obtainAvailableHours(
            @PathVariable Long canchaId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        try {
            List<LocalTime> hours = reservationService.getAvailableHours(canchaId, day);
            if (hours == null || hours.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(hours);
        } catch (CanchaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
