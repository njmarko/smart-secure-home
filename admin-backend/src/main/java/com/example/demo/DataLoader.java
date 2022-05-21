package com.example.demo;

import com.example.demo.model.Privilege;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;


    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        User user1 = new User();
        user1.setUsername("Pera");
        user1.setPassword("Pera");
        user1.setFirstName("Pera");
        user1.setLastName("Peric");
        user1.setEmail("pera@yahoo.com");
        user1.setEnabled(true);
        user1.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));

        Role superAdmin = new Role();
        superAdmin.setName("SUPER_ADMIN");

        Role admin = new Role();
        admin.setName("ROLE_ADMIN");

        roleRepository.save(superAdmin);
        roleRepository.save(admin);

        user1.setRoles(List.of(superAdmin));

        userRepository.save(user1);

    }
}
