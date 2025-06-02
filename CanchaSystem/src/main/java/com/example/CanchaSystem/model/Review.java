package com.example.CanchaSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)  // Cambié aquí el nombre de la columna FK
    private Client client;

    @ManyToOne
    @JoinColumn(name = "cancha_id", nullable = false)  // Cambié aquí el nombre de la columna FK
    private Cancha cancha;

    @Column(nullable = false)
    private double rating;

    @Column(nullable = true)
    private String message;
}
