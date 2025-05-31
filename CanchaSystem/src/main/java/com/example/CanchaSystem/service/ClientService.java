package com.example.CanchaSystem.service;

import com.example.CanchaSystem.exception.UsernameAlreadyExistsException;
import com.example.CanchaSystem.exception.client.ClientNotFoundException;
import com.example.CanchaSystem.exception.client.NoClientsException;
import com.example.CanchaSystem.model.Client;
import com.example.CanchaSystem.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client insertClient(Client client) throws UsernameAlreadyExistsException {
        if(!clientRepository.existsByUsername(client.getUsername()))
            return clientRepository.save(client);
        else throw new UsernameAlreadyExistsException("El nombre de usuario ya existe");
    }

    public List<Client> getAllClients() throws NoClientsException {
        if(!clientRepository.findAll().isEmpty()){
            return clientRepository.findAll();
        }else
            throw new NoClientsException("Todavia no hay clientes registrados");


    }

    public void updateClient(Client client) throws ClientNotFoundException {
        if(clientRepository.existsById(client.getId())){
            clientRepository.save(client);

        }else
            throw new ClientNotFoundException("Cliente no encontrado");


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
