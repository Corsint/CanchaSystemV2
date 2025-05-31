package com.example.CanchaSystem.repository;

import com.example.CanchaSystem.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long>{

    boolean existsById(Long id);

    boolean existsByUsername(String username);


}
