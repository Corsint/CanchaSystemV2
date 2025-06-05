package com.example.CanchaSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"matchDate", "cancha_id"})
)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id",nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "cancha_id",nullable = false)
    private Cancha cancha;

    @Column(nullable = false)
    @PastOrPresent
    private LocalDateTime reservationDate;

    @Column(nullable = false)
    @Future
    private LocalDateTime matchDate;

    @Column(nullable = false)
    @Min(1)
    private Double deposit;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.PENDING;

    @PrePersist
    public void prePersist() {
        if (reservationDate == null) {
            reservationDate = LocalDateTime.now();
        }
    }
}
