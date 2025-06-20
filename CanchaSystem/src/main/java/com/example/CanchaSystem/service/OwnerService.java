package com.example.CanchaSystem.service;

import com.example.CanchaSystem.exception.client.ClientNotFoundException;
import com.example.CanchaSystem.exception.misc.IllegalAmountException;
import com.example.CanchaSystem.exception.misc.UsernameAlreadyExistsException;
import com.example.CanchaSystem.exception.owner.NoOwnersException;
import com.example.CanchaSystem.exception.owner.OwnerNotFoundException;
import com.example.CanchaSystem.model.Client;
import com.example.CanchaSystem.model.Owner;
import com.example.CanchaSystem.model.Role;
import com.example.CanchaSystem.repository.OwnerRepository;
import com.example.CanchaSystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Owner insertOwner(Owner owner) throws UsernameAlreadyExistsException {
        Role ownerRole = roleRepository.findByName("OWNER")
                .orElseGet(() -> roleRepository.save(new Role("OWNER")));
        owner.setRole(ownerRole);

        if(!ownerRepository.existsByUsername(owner.getUsername())) {
            owner.setRole(ownerRole);

            owner.setPassword(passwordEncoder.encode(owner.getPassword()));
            return ownerRepository.save(owner);

        }  else throw new UsernameAlreadyExistsException("El nombre de usuario ya existe");
    }

    public List<Owner> getAllOwners() throws NoOwnersException {
        List<Owner> owners = ownerRepository.findAll();
        if(owners.isEmpty())
            throw new NoOwnersException("Todavia no hay dueños registrados");
        return owners;
    }

    public Owner updateOwner(Owner ownerFromRequest) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findById(ownerFromRequest.getId())
                .orElseThrow(() -> new ClientNotFoundException("Dueño no encontrado"));

        owner.setName(ownerFromRequest.getName());
        owner.setLastName(ownerFromRequest.getLastName());
        owner.setUsername(ownerFromRequest.getUsername());
        owner.setMail(ownerFromRequest.getMail());
        owner.setCellNumber(ownerFromRequest.getCellNumber());
        owner.setBankOwner(ownerFromRequest.getBankOwner());

        return ownerRepository.save(owner);
    }

    public Owner addMoneyToOwnerBank(long ownerId,double addedAmount){

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException("Dueño no encontrado"));

        if (addedAmount <= 0)
            throw new IllegalAmountException("Monto invalido");

        owner.setBankOwner(owner.getBankOwner()+addedAmount);
        return ownerRepository.save(owner);

    }

    public Owner updateOwnerAdmin(Owner ownerFromRequest) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findById(ownerFromRequest.getId())
                .orElseThrow(() -> new OwnerNotFoundException("Dueño no encontrado"));

        owner.setName(ownerFromRequest.getName());
        owner.setLastName(ownerFromRequest.getLastName());
        owner.setUsername(ownerFromRequest.getUsername());
        owner.setMail(ownerFromRequest.getMail());
        owner.setCellNumber(ownerFromRequest.getCellNumber());
        owner.setBankOwner(ownerFromRequest.getBankOwner());

        String pass = ownerFromRequest.getPassword();

        if (!pass.isEmpty()) {
            owner.setPassword(passwordEncoder.encode(pass));
        }

        return ownerRepository.save(owner);
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

