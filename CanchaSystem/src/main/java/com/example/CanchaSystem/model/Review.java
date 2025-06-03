package com.example.CanchaSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "cancha_id", nullable = false)
    private Cancha cancha;

    @Column(nullable = false)
    @Size(
            min = 1,
            max = 5,
            message = "Review only accepts values between 1 and 5"
    )
    private double rating;

    @Column(nullable = true)
    @Size(
            min = 5,
            max = 500,
            message = "Message only accepts caracters between 5 and 500"
    )
    private String message;
}
