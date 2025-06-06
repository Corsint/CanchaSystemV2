package com.example.CanchaSystem.repository;

import com.example.CanchaSystem.model.CanchaBrand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CanchaBrandRepository extends JpaRepository<CanchaBrand, Long> {
    public boolean existsByBrandName(String name);

    public List<CanchaBrand> findByOwnerId(long ownerId);
}
