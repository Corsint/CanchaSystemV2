package com.example.CanchaSystem.controller;

import com.example.CanchaSystem.exception.misc.UsernameAlreadyExistsException;
import com.example.CanchaSystem.exception.owner.NoOwnersException;
import com.example.CanchaSystem.exception.owner.OwnerNotFoundException;
import com.example.CanchaSystem.model.Owner;
import com.example.CanchaSystem.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    OwnerService ownerService;

    @PostMapping("/insert")
    public ResponseEntity<?> insertOwner(@Validated @RequestBody Owner owner) {
        try {
            return ResponseEntity.ok(ownerService.insertOwner(owner));
        } catch (UsernameAlreadyExistsException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error",e.getMessage()));
        }
    }

    @GetMapping("/findall")
    public ResponseEntity<?> getOwners() {
        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(ownerService.getAllOwners());
        } catch (NoOwnersException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",e.getMessage()));
        }

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateOwner(@RequestBody Owner owner) {
        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(ownerService.updateOwner(owner));
        } catch (OwnerNotFoundException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable Long id) {
        try {
            ownerService.deleteOwner(id);
            return ResponseEntity.ok(Map.of("message","Dueño eliminado"));
        } catch (OwnerNotFoundException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOwnerById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(ownerService.findOwnerById(id));
        } catch (OwnerNotFoundException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",e.getMessage()));
        }
    }
}
