package com.example.CanchaSystem.repository;

import com.example.CanchaSystem.model.Cancha;
import com.example.CanchaSystem.model.CanchaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CanchaRepository extends JpaRepository<Cancha,Long> {
    boolean existsById(Long id);
    boolean existsByAddress(String address);
    boolean existsByName(String name);

    Optional<List<Cancha>> findByActive(boolean active);
    Optional<List<Cancha>> findByCanchaType(CanchaType canchaType);
    Optional<List<Cancha>> findByHasRoof(boolean roof);
    Optional<List<Cancha>> findByCanShower(boolean shower);
    Optional<List<Cancha>> findByOrderByTotalAmountAsc();
    Optional<List<Cancha>> findByOrderByTotalAmountDesc();
}
