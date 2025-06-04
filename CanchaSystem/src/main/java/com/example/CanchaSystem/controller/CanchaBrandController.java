package com.example.CanchaSystem.controller;

import com.example.CanchaSystem.exception.cancha.CanchaNameAlreadyExistsException;
import com.example.CanchaSystem.exception.cancha.CanchaNotFoundException;
import com.example.CanchaSystem.exception.cancha.IllegalCanchaAddressException;
import com.example.CanchaSystem.exception.cancha.NoCanchasException;
import com.example.CanchaSystem.exception.canchaBrand.CanchaBrandNameAlreadyExistsException;
import com.example.CanchaSystem.exception.canchaBrand.CanchaBrandNotFoundException;
import com.example.CanchaSystem.exception.canchaBrand.NoCanchaBrandsException;
import com.example.CanchaSystem.model.Cancha;
import com.example.CanchaSystem.model.CanchaBrand;
import com.example.CanchaSystem.model.Owner;
import com.example.CanchaSystem.repository.OwnerRepository;
import com.example.CanchaSystem.service.CanchaBrandService;
import com.example.CanchaSystem.service.OwnerService;
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
@RequestMapping("/canchaBrand")
@PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
public class CanchaBrandController {

    @Autowired
    private CanchaBrandService canchaBrandService;

    @Autowired
    private OwnerRepository ownerRepository;

    @PostMapping("/insert")
    public ResponseEntity<?> insertCanchaBrand(@Validated @RequestBody CanchaBrand canchaBrand, Authentication auth) {

            String username = auth.getName();
            Optional<Owner> ownerOpt = ownerRepository.findByUsername(username);
            Owner owner = ownerOpt.get();

            canchaBrand.setOwner(owner);

            return ResponseEntity.status(HttpStatus.CREATED).body(canchaBrandService.insertCanchaBrand(canchaBrand));

    }

    @GetMapping("/findall")
    public ResponseEntity<?> getCanchaAllBrands() {
            return ResponseEntity.ok(canchaBrandService.getAllCanchaBrands());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCanchaBrand(@RequestBody CanchaBrand canchaBrand) {
            return ResponseEntity.ok(canchaBrandService.updateCanchaBrand(canchaBrand));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCanchaBrand(@PathVariable Long id) {
            canchaBrandService.deleteCanchaBrand(id);
            return ResponseEntity.ok(Map.of("message","Marca eliminada"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findCanchaBrandById(@PathVariable Long id) {
            return ResponseEntity.ok(canchaBrandService.findCanchaBrandById(id));
    }
}
