package com.example.CanchaSystem.controller;

import com.example.CanchaSystem.exception.misc.UsernameAlreadyExistsException;
import com.example.CanchaSystem.exception.admin.AdminNotFoundException;
import com.example.CanchaSystem.exception.admin.NoAdminsException;
import com.example.CanchaSystem.model.Admin;
import com.example.CanchaSystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/insert")
    public ResponseEntity<Admin> insertAdmin(@Validated @RequestBody Admin admin) {
        try {
            return ResponseEntity.ok(adminService.insertAdmin(admin));
        } catch (UsernameAlreadyExistsException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/findall")
    public List<Admin> getAdmins() {
        try {
            return adminService.getAllAdmins();
        } catch (NoAdminsException e) {
            System.err.println(e.getMessage());
            return null;
        }

    }

    @PutMapping("/update")
    public void updateAdmin(@RequestBody Admin admin) {
        try {
            adminService.updateAdmin(admin);
        } catch (AdminNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable Long id) {
        try {
            adminService.deleteAdmin(id);
        } catch (AdminNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Optional<Admin> findAdminById(@PathVariable Long id) {
        try {
            return Optional.ofNullable(adminService.findAdminById(id));
        } catch (AdminNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }
}
