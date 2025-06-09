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
    Optional<Cancha> findById(Long id);

    List<Cancha> findByBrandOwnerUsername(String username);
    List<Cancha> findByBrandOwnerIdAndActive(Long id,boolean active);
    List<Cancha> findByBrandOwnerId(Long id);
    List<Cancha> findByBrandIdAndActive(Long id,boolean active);
    List<Cancha> findByBrandId(Long id);
    List<Cancha> findByActive(boolean active);

    List<Cancha> findByCanchaTypeAndActive(CanchaType canchaType,boolean active);
    List<Cancha> findByHasRoofAndActive(boolean roof,boolean active);
    List<Cancha> findByCanShowerAndActive(boolean shower, boolean active);
    List<Cancha> findByActiveTrueOrderByTotalAmountAsc();
    List<Cancha> findByActiveTrueOrderByTotalAmountDesc();
}
