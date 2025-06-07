package com.example.CanchaSystem.controller;

import com.example.CanchaSystem.exception.misc.BankAlreadyLinkedException;
import com.example.CanchaSystem.exception.misc.CellNumberAlreadyAddedException;
import com.example.CanchaSystem.exception.misc.MailAlreadyRegisteredException;
import com.example.CanchaSystem.exception.misc.UsernameAlreadyExistsException;
import com.example.CanchaSystem.exception.client.ClientNotFoundException;
import com.example.CanchaSystem.exception.client.NoClientsException;
import com.example.CanchaSystem.model.Client;
import com.example.CanchaSystem.repository.ClientRepository;
import com.example.CanchaSystem.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/client")

public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/me")
    public ResponseEntity<?> getClientId(@AuthenticationPrincipal UserDetails userDetails) {
        Client client = clientRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ClientNotFoundException("Cliente no encontrado"));
        return ResponseEntity.ok(client.getId());
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insertClient(@Validated @RequestBody Client client) {
            return ResponseEntity.status(HttpStatus.CREATED).body(clientService.insertClient(client));
    }

    @GetMapping("/findall")
    public ResponseEntity<?> getClients() {
            return ResponseEntity.ok(clientService.getAllClients());
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/update")
    public ResponseEntity<?> updateClient(@RequestBody Client client, HttpServletRequest request) {
        clientService.updateClient(client);

        SecurityContextHolder.clearContext();
        request.getSession().invalidate();

        return ResponseEntity.ok("Datos actualizados, inicie sesión nuevamente");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
            clientService.deleteClient(id);
            return ResponseEntity.ok(Map.of("message","Cliente eliminado"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findClientById(@PathVariable Long id) {
            return ResponseEntity.ok(clientService.findClientById(id));
    }
}
