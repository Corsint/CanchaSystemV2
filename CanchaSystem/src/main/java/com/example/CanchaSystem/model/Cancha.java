package com.example.CanchaSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cancha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(
            min = 3,
            max = 15,
            message = "Name only accepts values between 3 and 15"
    )
    private String name;

    @Column(nullable = false, unique = true)
    private String address;

    @Column(nullable = false)
    @Min(1)
    private Double totalAmount;

    @Column(nullable = false)
    private LocalTime openingHour;

    @Column(nullable = false)
    private LocalTime closingHour;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private boolean hasRoof;

    @Column(nullable = false)
    private boolean canShower;

    //private boolean birthday;
    //private double birthday_price;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private CanchaBrand brand;

    @Column(nullable = false)
    private CanchaType canchaType;

}
