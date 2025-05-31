package com.example.CanchaSystem.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNullFields;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;


}
