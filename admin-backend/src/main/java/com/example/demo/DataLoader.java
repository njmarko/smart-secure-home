package com.example.demo;

import com.example.demo.model.Privilege;
import com.example.demo.model.RealEstate;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.PrivilegeRepository;
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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RealEstateRepository realEstateRepository;
    private final PrivilegeRepository privilegeRepository;
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

        // CREATE PRIVILEGES HERE...
        var createRealEstatePrivilege = createPrivilege("CREATE_REAL_ESTATE");
        var readMyRealEstatesPrivilege = createPrivilege("READ_MY_REAL_ESTATES");
        privilegeRepository.save(createRealEstatePrivilege);
        privilegeRepository.save(readMyRealEstatesPrivilege);

        // CREATE ROLES HERE...
        var adminRole = createRole("ROLE_ADMIN", createRealEstatePrivilege, readMyRealEstatesPrivilege);
        var superAdminRole = createRole("ROLE_SUPER_ADMIN", createRealEstatePrivilege, readMyRealEstatesPrivilege);
        roleRepository.save(adminRole);
        roleRepository.save(superAdminRole);

        user1.setRoles(List.of(superAdminRole));

        var home = new RealEstate("Kuca").addStakeholder(user1);
        var dogHouse = new RealEstate("Kuca za kera").addStakeholder(user1);
        var helperObject = new RealEstate("Pomocni objekat").addStakeholder(user1);

        user1.addRealEstate(home).addRealEstate(dogHouse).addRealEstate(helperObject);

        userRepository.save(user1);
        realEstateRepository.save(home);
        realEstateRepository.save(dogHouse);
        realEstateRepository.save(helperObject);
    }

    private Privilege createPrivilege(String name) {
        var privilege = new Privilege();
        privilege.setName(name);
        return privilege;
    }

    private Role createRole(String name, Privilege... privileges) {
        var role = new Role();
        role.setName(name);
        role.setPrivileges(Set.of(privileges));
        for (var p: privileges) {
            var roles = Objects.requireNonNullElse(p.getRoles(), new HashSet<Role>());
            roles.add(role);
            p.setRoles(roles);
        }
        return role;
    }
}
