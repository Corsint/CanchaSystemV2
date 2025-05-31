package com.example.CanchaSystem.controller;

import com.example.CanchaSystem.exception.cancha.CanchaNameAlreadyExistsException;
import com.example.CanchaSystem.exception.cancha.CanchaNotFoundException;
import com.example.CanchaSystem.exception.cancha.IllegalCanchaAddressException;
import com.example.CanchaSystem.exception.cancha.NoCanchasException;
import com.example.CanchaSystem.model.Cancha;
import com.example.CanchaSystem.service.CanchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cancha")
public class CanchaController {

    @Autowired
    private CanchaService canchaService;

    @PostMapping("/insert")
    public ResponseEntity<?> insertCancha(@Validated @RequestBody Cancha cancha) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(canchaService.insertCancha(cancha));

        } catch (CanchaNameAlreadyExistsException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error",e.getMessage()));
        } catch (IllegalCanchaAddressException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/findall")
    public List<Cancha> getCanchas() {
        try {
            return canchaService.getAllCanchas();
        } catch (NoCanchasException e) {
            System.err.println(e.getMessage());
            return null;
        }

    }

    @PutMapping("/update")
    public void updateCancha(@RequestBody Cancha cancha) {
        try {
            canchaService.updateCancha(cancha);
        } catch (CanchaNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCancha(@PathVariable Long id) {
        try {
            canchaService.deleteCancha(id);
        } catch (CanchaNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Optional<Cancha> findCanchaById(@PathVariable Long id) {
        try {
            return Optional.ofNullable(canchaService.findCanchaById(id));
        } catch (CanchaNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }
}
