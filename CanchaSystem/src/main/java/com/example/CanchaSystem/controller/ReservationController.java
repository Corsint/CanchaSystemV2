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
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
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
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/insert")
    public ResponseEntity<?> insertReservation(@Validated @RequestBody Reservation reservation) {
            return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.insertReservation(reservation));
    }

    @GetMapping("/findall")
    public ResponseEntity<?> getReservations() {
            return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateReservation(@RequestBody Reservation reservation) {
            return ResponseEntity.ok(reservationService.updateReservation(reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
            reservationService.deleteReservation(id);
            return ResponseEntity.ok(Map.of("message","Reserva eliminada"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findReservationById(@PathVariable Long id) {
            return ResponseEntity.ok(reservationService.findReservationById(id));
    }

    @GetMapping("/getMyReservations")
    public ResponseEntity<?> getAllMyReservations(Long clientid){
        return ResponseEntity.ok(reservationService.findReservationsByClientId(clientid));
    }

    @GetMapping("/getAvailableHours/{canchaId}/{day}")
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN') or hasRole('CLIENT')")
    public ResponseEntity<List<LocalTime>> obtainAvailableHours(
            @PathVariable Long canchaId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
            List<LocalTime> hours = reservationService.getAvailableHours(canchaId, day);
            if (hours == null || hours.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(hours);
    }

    @GetMapping("/getAllMyReservations")
    @PreAuthorize("hasRole('OWNER')")
    private ResponseEntity<?> getAllMyReservationsOwner(Long ownerId){
        return ResponseEntity.ok(reservationService.getReservationsByOwnerId(ownerId));
    }

    @GetMapping("/getAllMyReservationsByBrand")
    @PreAuthorize("hasRole('OWNER')")
    private ResponseEntity<?> getAllMyReservationsByBrand(Long brandId){
        return ResponseEntity.ok(reservationService.getReservationsByBrandId(brandId));
    }


}
