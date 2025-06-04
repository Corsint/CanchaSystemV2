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
            return ResponseEntity.status(HttpStatus.CREATED).body(ownerService.insertOwner(owner));
    }

    @GetMapping("/findall")
    public ResponseEntity<?> getOwners() {
            return ResponseEntity.ok(ownerService.getAllOwners());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateOwner(@RequestBody Owner owner) {
            return ResponseEntity.ok(ownerService.updateOwner(owner));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable Long id) {
            ownerService.deleteOwner(id);
            return ResponseEntity.ok(Map.of("message","Dueño eliminado"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOwnerById(@PathVariable Long id) {
            return ResponseEntity.ok(ownerService.findOwnerById(id));
    }
}
