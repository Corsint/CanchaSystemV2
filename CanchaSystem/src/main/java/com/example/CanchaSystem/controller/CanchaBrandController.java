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
@PreAuthorize("hasRole('ADMIN')")
public class CanchaBrandController {

    @Autowired
    private CanchaBrandService canchaBrandService;

    @Autowired
    private OwnerRepository ownerRepository;

    @PostMapping("/insert")
    public ResponseEntity<?> insertCanchaBrand(@Validated @RequestBody CanchaBrand canchaBrand, Authentication auth) {
        try {
            String username = auth.getName();
            Optional<Owner> ownerOpt = ownerRepository.findByUsername(username);
            Owner owner = ownerOpt.get();

            canchaBrand.setOwner(owner);

            return ResponseEntity.status(HttpStatus.CREATED).body(canchaBrandService.insertCanchaBrand(canchaBrand));

        } catch (CanchaBrandNameAlreadyExistsException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/findall")
    public List<CanchaBrand> getCanchaAllBrands() {
        try {
            return canchaBrandService.getAllCanchaBrands();
        } catch (NoCanchaBrandsException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @PutMapping("/update")
    public void updateCanchaBrand(@RequestBody CanchaBrand canchaBrand) {
        try {
            canchaBrandService.updateCanchaBrand(canchaBrand);
        } catch (CanchaBrandNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCanchaBrand(@PathVariable Long id) {
        try {
            canchaBrandService.deleteCanchaBrand(id);
        } catch (CanchaNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<CanchaBrand> findCanchaBrandById(@PathVariable Long id) {
        try {
            return Optional.ofNullable(canchaBrandService.findCanchaBrandById(id));
        } catch (CanchaBrandNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }
}
