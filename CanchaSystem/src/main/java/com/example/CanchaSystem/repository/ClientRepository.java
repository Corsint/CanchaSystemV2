package com.example.CanchaSystem.repository;

import com.example.CanchaSystem.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

   boolean existsById(Long id);

   boolean existsByUsername(String username);

   boolean existsByMail(String mail);

   boolean existsByBank(String bank);

   boolean existsByCellNumber(String cell_number);
}
