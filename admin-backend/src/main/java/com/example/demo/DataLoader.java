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
        // CREATE PRIVILEGES HERE...
        var createRealEstatePrivilege = createPrivilege("CREATE_REAL_ESTATE");
        var readMyRealEstatesPrivilege = createPrivilege("READ_MY_REAL_ESTATES");
        var deleteUsersPrivilege = createPrivilege("DELETE_USERS");
        var modifyRolePrivilege = createPrivilege("MODIFY_USER_ROLE");
        var blacklistJwt = createPrivilege("BLACKLIST_JWT");
        var readUsersPaginated = createPrivilege("READ_USERS");
        var registerUsersPrivilege = createPrivilege("REGISTER_USERS");
        var csrSignPrivilege = createPrivilege("SIGN_CSR");
        var readCsrDetailsPrivilege = createPrivilege("READ_CSR_DETAILS");
        var readCsrsPrivilege = createPrivilege("READ_CSRS");
        var readCertificatesPrivilege = createPrivilege("READ_CERTIFICATES");
        privilegeRepository.save(createRealEstatePrivilege);
        privilegeRepository.save(readMyRealEstatesPrivilege);
        privilegeRepository.save(deleteUsersPrivilege);
        privilegeRepository.save(modifyRolePrivilege);
        privilegeRepository.save(blacklistJwt);
        privilegeRepository.save(readUsersPaginated);
        privilegeRepository.save(registerUsersPrivilege);
        privilegeRepository.save(csrSignPrivilege);
        privilegeRepository.save(readCsrDetailsPrivilege);
        privilegeRepository.save(readCertificatesPrivilege);
        privilegeRepository.save(readCsrsPrivilege);

        // CREATE ROLES HERE...
        // Higher priority means that the role is more important
        // this is used when we look for possible user to assigned them to objects
        var adminRole = createRole("ROLE_ADMIN", 100,
                createRealEstatePrivilege,
                readMyRealEstatesPrivilege,
                deleteUsersPrivilege,
                modifyRolePrivilege,
                readUsersPaginated,
                registerUsersPrivilege,
                csrSignPrivilege,
                readCsrDetailsPrivilege,
                readCertificatesPrivilege,
                readCsrsPrivilege
        );
        var superAdminRole = createRole("ROLE_SUPER_ADMIN", 1000,
                createRealEstatePrivilege,
                readMyRealEstatesPrivilege,
                deleteUsersPrivilege,
                modifyRolePrivilege,
                blacklistJwt,
                readUsersPaginated,
                registerUsersPrivilege,
                readCsrDetailsPrivilege,
                csrSignPrivilege,
                readCertificatesPrivilege,
                readCsrsPrivilege
        );
        var ownerRole = createRole("ROLE_OWNER", 99,
                createRealEstatePrivilege,
                readMyRealEstatesPrivilege,
                modifyRolePrivilege,
                readUsersPaginated
        );
        var tenantRole = createRole("ROLE_TENANT", 98,
                readMyRealEstatesPrivilege,
                readUsersPaginated
        );
        roleRepository.saveAll(List.of(
                superAdminRole, adminRole, ownerRole, tenantRole
        ));

        // CREATE USERS HERE...
        var user1 = createUser("Pera", "Peric", "pera", "Test$123", superAdminRole);
        var user2 = createUser("Pera", "Peric", "manji pera", "Test$123", adminRole);
        var user3 = createUser("Pera", "Peric", "jos manji pera", "Test$123", ownerRole);
        var user4 = createUser("Pera", "Peric", "najmanji pera", "Test$123", tenantRole);
        var hajduk = createUser("Hajduk", "Dusan", "hajduk", "Hajduk1!", adminRole);


        // CREATE REAL ESTATES HERE...
        var home = new RealEstate("Kuca").addStakeholder(user1);
        var dogHouse = new RealEstate("Kuca za kera").addStakeholder(user1);
        var helperObject = new RealEstate("Pomocni objekat").addStakeholder(user1);

        user1.addRealEstate(home).addRealEstate(dogHouse).addRealEstate(helperObject);

        userRepository.saveAll(
                List.of(user1, user2, user3, user4, hajduk)
        );
        realEstateRepository.saveAll(
                List.of(home, dogHouse, helperObject)
        );
    }

    private User createUser(String firstName, String lastName, String username, String password, Role... roles) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(String.format("%s@gmail.com", username));
        user.setEnabled(true);
        user.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        user.setRoles(List.of(roles));
        return user;
    }

    private Privilege createPrivilege(String name) {
        var privilege = new Privilege();
        privilege.setName(name);
        return privilege;
    }

    private Role createRole(String name, Integer priority, Privilege... privileges) {
        var role = new Role();
        role.setName(name);
        role.setPriority(priority);
        role.setPrivileges(Set.of(privileges));
        for (var p : privileges) {
            var roles = Objects.requireNonNullElse(p.getRoles(), new HashSet<Role>());
            roles.add(role);
            p.setRoles(roles);
        }
        return role;
    }
}
