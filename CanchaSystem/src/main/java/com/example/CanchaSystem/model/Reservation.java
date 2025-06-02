package com.example.CanchaSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
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
    @Future
    private LocalDateTime reservationDate;

    @Column(nullable = false,unique = true)
    @Future
    private LocalDateTime matchDate;

    @Column(nullable = false)
    @Min(1)
    private Double deposit;
}
