package com.example.CanchaSystem.repository;

import com.example.CanchaSystem.model.CanchaBrand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CanchaBrandRepository extends JpaRepository<CanchaBrand, Long> {
    public boolean existsByBrandName(String name);
}
