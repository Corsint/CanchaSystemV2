package com.example.CanchaSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

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
    private Date reservationDate;

    @Column(nullable = false,unique = true)
    private Date matchDate;

    @Column(nullable = false)
    private Double deposit;
}
