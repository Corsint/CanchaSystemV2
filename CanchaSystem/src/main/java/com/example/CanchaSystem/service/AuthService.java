package com.example.CanchaSystem.service;

import com.example.CanchaSystem.model.Admin;
import com.example.CanchaSystem.model.Client;
import com.example.CanchaSystem.model.Owner;
import com.example.CanchaSystem.repository.AdminRepository;
import com.example.CanchaSystem.repository.ClientRepository;
import com.example.CanchaSystem.repository.OwnerRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthService implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final OwnerRepository ownerRepository;
    private final AdminRepository adminRepository;

    public AuthService(ClientRepository clientRepository, OwnerRepository ownerRepository, AdminRepository adminRepository) {
        this.clientRepository = clientRepository;
        this.ownerRepository = ownerRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. Buscar en CLIENT
        Client client = clientRepository.findByUsername(username).orElse(null);
        if (client != null) {
            return new org.springframework.security.core.userdetails.User(
                    client.getUsername(),
                    client.getPassword(),
                    client.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                            .collect(Collectors.toSet())
            );
        }

        // 2. Buscar en OWNER
        Owner owner = ownerRepository.findByUsername(username).orElse(null);
        if (owner != null) {
            return new org.springframework.security.core.userdetails.User(
                    owner.getUsername(),
                    owner.getPassword(),
                    owner.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                            .collect(Collectors.toSet())
            );
        }

        // 3. Buscar en ADMIN
        Admin admin = adminRepository.findByUsername(username).orElse(null);
        if (admin != null) {
            return new org.springframework.security.core.userdetails.User(
                    admin.getUsername(),
                    admin.getPassword(),
                    admin.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                            .collect(Collectors.toSet())
            );
        }

        // Si no se encuentra en ningún lado
        throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }
}
