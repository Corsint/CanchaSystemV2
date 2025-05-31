package com.example.CanchaSystem.service;

import com.example.CanchaSystem.exception.UsernameAlreadyExistsException;
import com.example.CanchaSystem.exception.owner.NoOwnersException;
import com.example.CanchaSystem.exception.owner.OwnerNotFoundException;
import com.example.CanchaSystem.model.Owner;
import com.example.CanchaSystem.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public Owner insertOwner(Owner owner) throws UsernameAlreadyExistsException {
        if(!ownerRepository.existsByUsername(owner.getUsername()))
            return ownerRepository.save(owner);
        else throw new UsernameAlreadyExistsException("El nombre de usuario ya existe");
    }

    public List<Owner> getAllOwners() throws NoOwnersException {
        if(!ownerRepository.findAll().isEmpty()){
            return ownerRepository.findAll();
        }else
            throw new NoOwnersException("Todavia no hay dueños registrados");


    }

    public void updateOwner(Owner owner) throws OwnerNotFoundException {
        if(ownerRepository.existsById(owner.getId())){
            ownerRepository.save(owner);

        }else
            throw new OwnerNotFoundException("Dueño no encontrado");


    }

    public void deleteOwner(Long id) throws OwnerNotFoundException{
        if (ownerRepository.existsById(id)) {
            ownerRepository.deleteById(id);
        }else
            throw new OwnerNotFoundException("Dueño no encontrado");

    }

    public Owner findOwnerById(Long id) throws OwnerNotFoundException {
        return ownerRepository.findById(id).orElseThrow(()-> new OwnerNotFoundException("Dueño no encontrado"));
    }
}

