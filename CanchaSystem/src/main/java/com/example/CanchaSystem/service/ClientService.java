package com.example.CanchaSystem.service;

import com.example.CanchaSystem.exception.misc.*;
import com.example.CanchaSystem.exception.client.ClientNotFoundException;
import com.example.CanchaSystem.exception.client.NoClientsException;
import com.example.CanchaSystem.model.Client;
import com.example.CanchaSystem.model.Role;
import com.example.CanchaSystem.repository.ClientRepository;
import com.example.CanchaSystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Client insertClient(Client client) {
        if (clientRepository.existsByUsername(client.getUsername())) {
            throw new UsernameAlreadyExistsException("El nombre de usuario ya existe");
        }

        if (clientRepository.existsByMail(client.getMail())) {
            throw new MailAlreadyRegisteredException("El correo ya esta registrado");
        }

        if (clientRepository.existsByCellNumber(client.getCellNumber())) {
            throw new CellNumberAlreadyAddedException("El numero ya esta añadido");
        }

        Role clientRole = roleRepo.findByName("CLIENT")
                .orElseGet(() -> roleRepo.save(new Role("CLIENT")));
        client.setRole(clientRole);

        client.setPassword(passwordEncoder.encode(client.getPassword()));

        return clientRepository.save(client);
    }

    public List<Client> getAllClients() throws NoClientsException {
        List<Client> clients = clientRepository.findAll();
        if(clients.isEmpty())
            throw new NoClientsException("Todavia no hay clientes registrados");
        return clients;

    }

    public Client updateClient(Client clientFromRequest) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientFromRequest.getId())
                .orElseThrow(() -> new ClientNotFoundException("Cliente no encontrado"));

        client.setName(clientFromRequest.getName());
        client.setLastName(clientFromRequest.getLastName());
        client.setUsername(clientFromRequest.getUsername());
        client.setMail(clientFromRequest.getMail());
        client.setCellNumber(clientFromRequest.getCellNumber());

        return clientRepository.save(client);
    }

    public Client updateClientAdmin(Client clientFromRequest) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientFromRequest.getId())
                .orElseThrow(() -> new ClientNotFoundException("Cliente no encontrado"));

        client.setName(clientFromRequest.getName());
        client.setLastName(clientFromRequest.getLastName());
        client.setUsername(clientFromRequest.getUsername());
        client.setMail(clientFromRequest.getMail());
        client.setCellNumber(clientFromRequest.getCellNumber());
        client.setBankClient(clientFromRequest.getBankClient());

        String pass = clientFromRequest.getPassword();

        if (!pass.isEmpty()) {
            client.setPassword(passwordEncoder.encode(pass));
        }

        return clientRepository.save(client);
    }

    public Client addMoneyToClientBank(long clientId,double addedAmount){

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Cliente no encontrado"));

        if (addedAmount <= 0)
            throw new IllegalAmountException("Monto invalido");

        client.setBankClient(client.getBankClient()+addedAmount);
        return clientRepository.save(client);

    }

    public Client payFromClientBank(long clientId,double amountToPay){

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Cliente no encontrado"));

        if (amountToPay <= 0)
            throw new IllegalAmountException("Monto invalido");

        client.setBankClient(client.getBankClient()-amountToPay);
        return clientRepository.save(client);

    }

    public void deleteClient(Long id) throws ClientNotFoundException{
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        }else
            throw new ClientNotFoundException("Cliente no encontrado");

    }

    public Client findClientById(Long id) throws ClientNotFoundException {
        return clientRepository.findById(id).orElseThrow(()-> new ClientNotFoundException("Cliente no encontrado"));
    }
}
