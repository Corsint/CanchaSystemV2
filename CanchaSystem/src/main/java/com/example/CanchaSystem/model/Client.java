package com.example.CanchaSystem.model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,unique = true)
    private String mail;

    @Column(nullable = true,unique = true)
    private String cellNumber;

    @Column(nullable = true,unique = true)
    private String bank;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "client_roles",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}



