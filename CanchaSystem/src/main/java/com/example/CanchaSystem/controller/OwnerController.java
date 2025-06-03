package com.example.CanchaSystem.controller;

import com.example.CanchaSystem.exception.misc.UsernameAlreadyExistsException;
import com.example.CanchaSystem.exception.owner.NoOwnersException;
import com.example.CanchaSystem.exception.owner.OwnerNotFoundException;
import com.example.CanchaSystem.model.Owner;
import com.example.CanchaSystem.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/owner")
@PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")

public class OwnerController {

    @Autowired
    OwnerService ownerService;

    @PostMapping("/insert")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Owner> insertOwner(@Validated @RequestBody Owner owner) {
        try {
            return ResponseEntity.ok(ownerService.insertOwner(owner));
        } catch (UsernameAlreadyExistsException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/findall")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Owner> getOwners() {
        try {
            return ownerService.getAllOwners();
        } catch (NoOwnersException e) {
            System.err.println(e.getMessage());
            return null;
        }

    }

    @PutMapping("/update")
    public void updateOwner(@RequestBody Owner owner) {
        try {
            ownerService.updateOwner(owner);
        } catch (OwnerNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteOwner(@PathVariable Long id) {
        try {
            ownerService.deleteOwner(id);
        } catch (OwnerNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<Owner> findOwnerById(@PathVariable Long id) {
        try {
            return Optional.ofNullable(ownerService.findOwnerById(id));
        } catch (OwnerNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }
}
