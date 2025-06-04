package com.example.CanchaSystem.service;

import com.example.CanchaSystem.exception.misc.BankAlreadyLinkedException;
import com.example.CanchaSystem.exception.misc.CellNumberAlreadyAddedException;
import com.example.CanchaSystem.exception.misc.MailAlreadyRegisteredException;
import com.example.CanchaSystem.exception.misc.UsernameAlreadyExistsException;
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

    public Client insertClient(Client client)
            throws UsernameAlreadyExistsException,
            MailAlreadyRegisteredException,
            CellNumberAlreadyAddedException,
            BankAlreadyLinkedException {
        if(!clientRepository.existsByUsername(client.getUsername()))
            if(!clientRepository.existsByMail(client.getMail()))
                if(!clientRepository.existsByCellNumber(client.getCellNumber()))
                    if(!clientRepository.existsByBank(client.getBank()))
                        return clientRepository.save(client);
                    else
                        throw new BankAlreadyLinkedException("La cuenta ya esta vinculada");
                else
                    throw new CellNumberAlreadyAddedException("El numero ya esta añadido");
            else
                throw new MailAlreadyRegisteredException("El correo ya esta registrado");
        else
            throw new UsernameAlreadyExistsException("El nombre de usuario ya existe");
    }

    public List<Client> getAllClients() throws NoClientsException {
        List<Client> clientes = clientRepository.findAll();
        if(!clientes.isEmpty()){
            return clientes;
        }else
            throw new NoClientsException("Todavia no hay clientes registrados");
    }

    public Client updateClient(Client client) throws ClientNotFoundException {
        if(clientRepository.existsById(client.getId())){
            return clientRepository.save(client);
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
