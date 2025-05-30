package com.example.CanchaSystem.repository;

import com.example.CanchaSystem.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
