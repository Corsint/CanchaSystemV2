package com.example.CanchaSystem.repository;

import com.example.CanchaSystem.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

   boolean existsById(Long id);
   boolean existsByUsername(String username);
   boolean existsByMail(String mail);
   boolean existsByCellNumber(String cellNumber);

   Optional<Client> findByUsernameAndActive(String username,boolean active);

}
