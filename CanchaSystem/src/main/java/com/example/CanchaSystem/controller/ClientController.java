package com.example.CanchaSystem.controller;

import com.example.CanchaSystem.model.Client;
import com.example.CanchaSystem.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/insert")
    public Client insertClient(@RequestBody Client client) {
        return clientService.insertClient(client);
    }

    @GetMapping("/findall")
    public List<Client> getClients() {
        return clientService.getAllClients();
    }

    @PutMapping("/update")
    public Client updateClient(@RequestBody Client client) {
        return clientService.updateClient(client);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }

    @GetMapping("/{id}")
    public Client findClientById(@PathVariable Long id) {
        return clientService.findClientById(id);
    }
}
