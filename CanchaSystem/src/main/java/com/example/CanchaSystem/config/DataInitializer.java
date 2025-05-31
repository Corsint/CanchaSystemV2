package com.example.CanchaSystem.config;

import com.example.CanchaSystem.model.Admin;
import com.example.CanchaSystem.model.Client;
import com.example.CanchaSystem.model.Owner;
import com.example.CanchaSystem.model.Role;
import com.example.CanchaSystem.repository.AdminRepository;
import com.example.CanchaSystem.repository.ClientRepository;
import com.example.CanchaSystem.repository.OwnerRepository;
import com.example.CanchaSystem.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RoleRepository roleRepo,
            ClientRepository clientRepo,
            OwnerRepository ownerRepo,
            AdminRepository adminRepo,
            PasswordEncoder encoder
    ) {
        return args -> {
            // Buscar o crear roles
            Role adminRole = roleRepo.findByName("ADMIN")
                    .orElseGet(() -> roleRepo.save(new Role("ADMIN")));
            Role clientRole = roleRepo.findByName("CLIENT")
                    .orElseGet(() -> roleRepo.save(new Role("CLIENT")));
            Role ownerRole = roleRepo.findByName("OWNER")
                    .orElseGet(() -> roleRepo.save(new Role("OWNER")));

            // Crear un cliente si no existe
            if (clientRepo.findByUsername("cliente").isEmpty()) {
                Client client = new Client();
                client.setUsername("cliente");
                client.setPassword(encoder.encode("1234"));
                client.setRoles(Set.of(clientRole));
                client.setMail("dkflaj@fdkalj");
                client.setBank("test");
                client.setCellNumber("22");
                clientRepo.save(client);
            }

            // Crear un owner si no existe
            if (ownerRepo.findByUsername("duenio").isEmpty()) {
                Owner owner = new Owner();
                owner.setUsername("duenio");
                owner.setPassword(encoder.encode("1234"));
                owner.setRoles(Set.of(ownerRole));
                owner.setMail("a");
                owner.setCellNumber("2");
                ownerRepo.save(owner);
            }

            // Crear un admin si no existe
            if (adminRepo.findByUsername("admin").isEmpty()) {
                Admin admin = new Admin();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("1234"));
                admin.setRoles(Set.of(adminRole));
                adminRepo.save(admin);
            }

            System.out.println("✔ Datos iniciales cargados");
        };
    }
}
