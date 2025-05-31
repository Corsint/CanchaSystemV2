package com.example.CanchaSystem.repository;

import com.example.CanchaSystem.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    boolean existsById(Long id);

    boolean existsByUsername(String username);

    boolean existsByMail(String mail);

    boolean existsByCellNumber(String cellNumber);

    Optional<Owner> findByUsername(String username);

}
