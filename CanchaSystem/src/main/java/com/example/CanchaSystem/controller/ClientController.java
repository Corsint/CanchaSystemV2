package com.example.CanchaSystem.controller;

import com.example.CanchaSystem.exception.misc.BankAlreadyLinkedException;
import com.example.CanchaSystem.exception.misc.CellNumberAlreadyAddedException;
import com.example.CanchaSystem.exception.misc.MailAlreadyRegisteredException;
import com.example.CanchaSystem.exception.misc.UsernameAlreadyExistsException;
import com.example.CanchaSystem.exception.client.ClientNotFoundException;
import com.example.CanchaSystem.exception.client.NoClientsException;
import com.example.CanchaSystem.model.Client;
import com.example.CanchaSystem.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/client")
@PreAuthorize("hasRole('CLIENT')")

public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/insert")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> insertClient(@Validated @RequestBody Client client) {
            return ResponseEntity.status(HttpStatus.CREATED).body(clientService.insertClient(client));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/findall")
    public ResponseEntity<?> getClients() {
            return ResponseEntity.ok(clientService.getAllClients());
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/update")
    public ResponseEntity<?> updateClient(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.updateClient(client));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
            clientService.deleteClient(id);
            return ResponseEntity.ok(Map.of("message","Cliente eliminado"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findClientById(@PathVariable Long id) {
            return ResponseEntity.ok(clientService.findClientById(id));
    }
}
