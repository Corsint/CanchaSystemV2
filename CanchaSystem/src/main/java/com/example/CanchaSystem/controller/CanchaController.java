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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCanchas() {
        try {
            List<Cancha> canchas = canchaService.getAllCanchas();
            return ResponseEntity.ok(canchas);
        } catch (NoCanchasException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("findMyCanchas")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> getAllMyCanchas(Authentication auth) {
        String username = auth.getName();
        try {
            List<Cancha> canchas = canchaService.getCanchasByOwner(username);
            return ResponseEntity.ok(canchas);
        } catch (NoCanchasException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/updateAny")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAnyCancha(@RequestBody Cancha cancha) {
        try {
            canchaService.updateCancha(cancha);
            return ResponseEntity.ok(Map.of("message", "Cancha actualizada correctamente"));
        } catch (CanchaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/updateMyCancha")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> updateMyCancha(@RequestBody Cancha cancha, Authentication auth) {
        String username = auth.getName();

        try {
            canchaService.updateOwnerCancha(cancha, username);
            return ResponseEntity.ok(Map.of("message", "Cancha actualizada correctamente"));
        } catch (CanchaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/dropCanchaById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAnyCancha(@PathVariable Long id) {
        try {
            canchaService.deleteCancha(id);
            return ResponseEntity.ok(Map.of("message", "Cancha eliminada correctamente"));
        } catch (CanchaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/dropMyCanchaById/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> deleteMyCancha(@PathVariable Long id, Authentication auth) {
        String username = auth.getName();

        try {
            canchaService.deleteOwnerCancha(id, username);
            return ResponseEntity.ok(Map.of("message", "Cancha eliminada correctamente"));
        } catch (CanchaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/findCanchaById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<Cancha> findAnyCanchaById(@PathVariable Long id) {
        try {
            return Optional.ofNullable(canchaService.findCanchaById(id));
        } catch (CanchaNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }
}
