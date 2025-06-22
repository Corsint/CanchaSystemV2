package com.example.CanchaSystem.service;

import com.example.CanchaSystem.exception.client.ClientNotFoundException;
import com.example.CanchaSystem.exception.requests.NoRequestsException;
import com.example.CanchaSystem.exception.client.ClientAlreadyRequestedException;
import com.example.CanchaSystem.exception.requests.RequestNotFoundException;
import com.example.CanchaSystem.model.*;
import com.example.CanchaSystem.repository.ClientRepository;
import com.example.CanchaSystem.repository.OwnerRepository;
import com.example.CanchaSystem.repository.OwnerRequestRepository;
import com.example.CanchaSystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerRequestService {

    @Autowired
    OwnerRequestRepository ownerRequestRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ClientRepository clientRepository;

    public OwnerRequest insertRequest(OwnerRequest ownerRequest){

        if(ownerRequestRepository.existsByClientIdAndStatus(ownerRequest.getClient().getId(), OwnerRequestStatus.PENDING))
            throw new ClientAlreadyRequestedException("El usuario ya tiene una solicitud pendiente");

        return ownerRequestRepository.save(ownerRequest);
    }

    public List<OwnerRequest> getAllRequests(){
        List<OwnerRequest> requests = ownerRequestRepository.findAll();
        if (requests.isEmpty())
            throw new NoRequestsException("Todavia no hay solicitudes registradas");
        return requests;
    }

    public List<OwnerRequest> getAllPendingRequests(){
        List<OwnerRequest> requests = ownerRequestRepository.findByStatusAndActive(OwnerRequestStatus.PENDING, true);
        if (requests.isEmpty())
            throw new NoRequestsException("Todavia no hay solicitudes registradas");
        return requests;
    }

    public OwnerRequest updateRequest(Long id,OwnerRequestStatus status){
        OwnerRequest ownerRequest = ownerRequestRepository.findById(id)
                .orElseThrow(()->new RequestNotFoundException("No se encontro la solicitud"));

        ownerRequest.setStatus(status);
        return ownerRequestRepository.save(ownerRequest);
    }

    @Scheduled(fixedRate = 300000) // cada 300 segundos
    public void completeApprovedRequests() {
        List<OwnerRequest> approved = ownerRequestRepository.findByStatusAndActive(OwnerRequestStatus.APPROVED, true);

        for (OwnerRequest request : approved){
            Client approvedClient = clientRepository.findById(request.getClient().getId())
                    .orElseThrow(()-> new ClientNotFoundException("Cliente no encontrado"));

            Owner newOwner = new Owner();

            newOwner.setName(approvedClient.getName());
            newOwner.setLastName(approvedClient.getLastName());
            newOwner.setUsername(approvedClient.getUsername());
            newOwner.setPassword(approvedClient.getPassword());
            newOwner.setMail(approvedClient.getMail());
            newOwner.setCellNumber(approvedClient.getCellNumber());
            newOwner.setBankOwner(approvedClient.getBankClient());

            Role ownerRole = roleRepository.findByName("OWNER")
                    .orElseGet(() -> roleRepository.save(new Role("OWNER")));

            if(!ownerRepository.existsByUsername(approvedClient.getUsername())) {
                newOwner.setRole(ownerRole);
            }

            approvedClient.setActive(false);
            newOwner.setActive(true);
            request.setActive(false);

            clientRepository.save(approvedClient);
            ownerRepository.save(newOwner);
            ownerRequestRepository.save(request);
        }
    }
}
