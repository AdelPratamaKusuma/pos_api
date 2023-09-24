package com.bootcamp.posapi.config;

import com.bootcamp.posapi.entity.RoleEntity;
import com.bootcamp.posapi.entity.UserEntity;
import com.bootcamp.posapi.repository.RoleRepo;
import com.bootcamp.posapi.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DbInit implements CommandLineRunner {
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;


    @Override
    public void run(String... args) throws Exception {
        initRoleAndUser();
    }
    public void initRoleAndUser() {
        if (roleRepo.findAll().isEmpty()) {
            try {
                roleRepo.saveAll(List.of(
                        new RoleEntity("ROLE_ADMIN"),
                        new RoleEntity("ROLE_USER"),
                        new RoleEntity("ROLE_SUPER_USER")
                ));
                log.info("Save role success..!");
            } catch (Exception e) {
                log.error("Save role failed, error: {}", e.getMessage());
            }
        }

        if (userRepo.findAll().isEmpty()) {
            // admin
            RoleEntity adminRole = roleRepo.findByName("ROLE_ADMIN").orElse(null);
            if (adminRole != null) {
                UserEntity admin = new UserEntity("Admin", "User", "admin@gmail.com", encoder.encode("P@ssW0rd32!"), List.of(adminRole));
                try {
                    userRepo.save(admin);
                    log.info("Create admin role success..!");
                } catch (Exception e) {
                    log.error("Create admin role failed, Error: {}", e.getMessage());
                }
            }

            // user
            RoleEntity userRole = roleRepo.findByName("ROLE_USER").orElse(null);
            if (adminRole != null) {
                UserEntity user = new UserEntity("User", "Role", "user@gmail.com", encoder.encode("P@ssW0rd32!"), List.of(userRole));
                try {
                    userRepo.save(user);
                    log.info("Create user role success..!");
                } catch (Exception e) {
                    log.error("Create user role failed, Error: {}", e.getMessage());
                }
            }

            // super user
            RoleEntity superUserRole = roleRepo.findByName("ROLE_SUPER_USER").orElse(null);
            if (superUserRole != null) {
                UserEntity superUser = new UserEntity("Super", "User", "super.user@gmail.com", encoder.encode("P@ssW0rd32!"), List.of(superUserRole));
                try {
                    userRepo.save(superUser);
                    log.info("Create super user role success..!");
                } catch (Exception e) {
                    log.error("Create super user role failed, Error: {}", e.getMessage());
                }
            }
        }
    }
}
