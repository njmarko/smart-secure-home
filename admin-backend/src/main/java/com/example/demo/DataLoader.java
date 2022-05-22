package com.example.demo;

import com.example.demo.model.RealEstate;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RealEstateRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RealEstateRepository realEstateRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        User user1 = new User();
        user1.setUsername("pera");
        user1.setPassword(passwordEncoder.encode("Test$123"));
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

        var home = new RealEstate("Kuca").addStakeholder(user1);
        var dogHouse = new RealEstate("Kuca za kera").addStakeholder(user1);
        var helperObject = new RealEstate("Pomocni objekat").addStakeholder(user1);

        user1.addRealEstate(home).addRealEstate(dogHouse).addRealEstate(helperObject);

        userRepository.save(user1);
        realEstateRepository.save(home);
        realEstateRepository.save(dogHouse);
        realEstateRepository.save(helperObject);

    }
}
