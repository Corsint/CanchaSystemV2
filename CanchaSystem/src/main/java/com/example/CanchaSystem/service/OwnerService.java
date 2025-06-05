package com.example.CanchaSystem.service;

import com.example.CanchaSystem.exception.misc.UsernameAlreadyExistsException;
import com.example.CanchaSystem.exception.owner.NoOwnersException;
import com.example.CanchaSystem.exception.owner.OwnerNotFoundException;
import com.example.CanchaSystem.model.Owner;
import com.example.CanchaSystem.model.Role;
import com.example.CanchaSystem.repository.OwnerRepository;
import com.example.CanchaSystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Owner insertOwner(Owner owner) throws UsernameAlreadyExistsException {
        Role ownerRole = roleRepository.findByName("OWNER")
                .orElseGet(() -> roleRepository.save(new Role("OWNER")));
        owner.setRole(ownerRole);

        if(!ownerRepository.existsByUsername(owner.getUsername())) {
            owner.setRole(ownerRole);

            return ownerRepository.save(owner);

        }  else throw new UsernameAlreadyExistsException("El nombre de usuario ya existe");
    }

    public List<Owner> getAllOwners() throws NoOwnersException {
        if(!ownerRepository.findAll().isEmpty()){
            return ownerRepository.findAll();
        }else
            throw new NoOwnersException("Todavia no hay dueños registrados");


    }

    public Owner updateOwner(Owner owner) throws OwnerNotFoundException {
        if(ownerRepository.existsById(owner.getId())){
           return ownerRepository.save(owner);

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

