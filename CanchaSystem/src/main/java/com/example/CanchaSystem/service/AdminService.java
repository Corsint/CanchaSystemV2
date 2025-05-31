package com.example.CanchaSystem.service;

import com.example.CanchaSystem.exception.misc.UsernameAlreadyExistsException;
import com.example.CanchaSystem.exception.admin.AdminNotFoundException;
import com.example.CanchaSystem.exception.admin.NoAdminsException;
import com.example.CanchaSystem.model.Admin;
import com.example.CanchaSystem.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin insertAdmin(Admin admin) throws UsernameAlreadyExistsException {
        if(!adminRepository.existsByUsername(admin.getUsername()))
            return adminRepository.save(admin);
        else throw new UsernameAlreadyExistsException("El nombre de usuario ya existe");
    }

    public List<Admin> getAllAdmins() throws NoAdminsException {
        if(!adminRepository.findAll().isEmpty()){
            return adminRepository.findAll();
        }else
            throw new NoAdminsException("Todavia no hay administradores registrados");


    }

    public void updateAdmin(Admin admin) throws AdminNotFoundException {
        if(adminRepository.existsById(admin.getId())){
            adminRepository.save(admin);

        }else
            throw new AdminNotFoundException("Administrador no encontrado");


    }

    public void deleteAdmin(Long id) throws AdminNotFoundException{
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
        }else
            throw new AdminNotFoundException("Administrador no encontrado");

    }

    public Admin findAdminById(Long id) throws AdminNotFoundException {
        return adminRepository.findById(id).orElseThrow(()-> new AdminNotFoundException("Administrador no encontrado"));
    }
}
