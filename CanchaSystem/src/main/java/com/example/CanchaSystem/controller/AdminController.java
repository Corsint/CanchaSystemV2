package com.example.CanchaSystem.controller;

import com.example.CanchaSystem.exception.misc.UsernameAlreadyExistsException;
import com.example.CanchaSystem.exception.admin.AdminNotFoundException;
import com.example.CanchaSystem.exception.admin.NoAdminsException;
import com.example.CanchaSystem.model.Admin;
import com.example.CanchaSystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {


    @Autowired
    private AdminService adminService;

    @PostMapping("/insert")
    public ResponseEntity<?> insertAdmin(@Validated @RequestBody Admin admin) {
        try {
            return ResponseEntity.ok(adminService.insertAdmin(admin));
        } catch (UsernameAlreadyExistsException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.ok(Map.of("error",e.getMessage()));
        }
    }

    @GetMapping("/findall")
    public ResponseEntity<?> getAdmins() {
        try {
            return ResponseEntity.ok(adminService.getAllAdmins());
        } catch (NoAdminsException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.ok(Map.of("error",e.getMessage()));
        }

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAdmin(@RequestBody Admin admin) {
        try {
            return ResponseEntity.ok(adminService.updateAdmin(admin));
        } catch (AdminNotFoundException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.ok(Map.of("error",e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long id) {
        try {
            adminService.deleteAdmin(id);
            return ResponseEntity.ok(Map.of("message","Administrador eliminado"));
        } catch (AdminNotFoundException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.ok(Map.of("error",e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findAdminById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(adminService.findAdminById(id));
        } catch (AdminNotFoundException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.ok(Map.of("error",e.getMessage()));
        }
    }
}
