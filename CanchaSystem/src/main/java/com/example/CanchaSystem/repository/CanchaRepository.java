package com.example.CanchaSystem.repository;

import com.example.CanchaSystem.model.Cancha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanchaRepository extends JpaRepository<Cancha,Long> {
    boolean existsById(Long id);
    boolean existsByAddress(String address);
    boolean existsByName(String name);
}
