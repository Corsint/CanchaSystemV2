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
    boolean existsByIdAndBrandOwnerUsername(Long id, String username);

    List<Cancha> findByBrandOwnerUsername(String username);
    List<Cancha> findByBrandOwnerId(Long id);
    List<Cancha> findByBrandId(Long id);
    List<Cancha> findByActive(boolean active);
    List<Cancha> findByCanchaType(CanchaType canchaType);
    List<Cancha> findByHasRoof(boolean roof);
    List<Cancha> findByCanShower(boolean shower);
    List<Cancha> findByOrderByTotalAmountAsc();
    List<Cancha> findByOrderByTotalAmountDesc();
}
