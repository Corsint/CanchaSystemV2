package com.example.CanchaSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cancha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String address;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private Double total_amount;

    @Column(nullable = false)
    private boolean is_reserved;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private boolean has_roof;

    @Column(nullable = false)
    private boolean can_shower;

    //private boolean birthday;
    //private double birthday_price;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)  // Cambié aquí el nombre de la columna FK
    private Owner owner;

    @Column(nullable = false)
    private CanchaType canchaType;

}
