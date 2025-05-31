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

    @PostMapping("/insert")
    public ResponseEntity<?> insertClient(@Validated @RequestBody Client client) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(clientService.insertClient(client));

        } catch (UsernameAlreadyExistsException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error",e.getMessage()));
        } catch (MailAlreadyRegisteredException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.ok(null);
        }catch (CellNumberAlreadyAddedException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.ok(null);
        }catch (BankAlreadyLinkedException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/findall")
    public List<Client> getClients() {
        try {
            return clientService.getAllClients();
        } catch (NoClientsException e) {
            System.err.println(e.getMessage());
            return null;
        }

    }

    @PutMapping("/update")
    public void updateClient(@RequestBody Client client) {
        try {
            clientService.updateClient(client);
        } catch (ClientNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        try {
            clientService.deleteClient(id);
        } catch (ClientNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Optional<Client> findClientById(@PathVariable Long id) {
        try {
            return Optional.ofNullable(clientService.findClientById(id));
        } catch (ClientNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }
}
