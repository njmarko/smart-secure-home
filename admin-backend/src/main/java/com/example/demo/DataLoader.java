package com.example.demo;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.example.demo.model.Privilege;
import com.example.demo.model.RealEstate;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.PrivilegeRepository;
import com.example.demo.repository.RealEstateRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

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
        Privilege createRealEstatePrivilege = createPrivilege("CREATE_REAL_ESTATE");
        Privilege readMyRealEstatesPrivilege = createPrivilege("READ_MY_REAL_ESTATES");
        Privilege deleteUsersPrivilege = createPrivilege("DELETE_USERS");
        Privilege modifyRolePrivilege = createPrivilege("MODIFY_USER_ROLE");
        Privilege blacklistJwt = createPrivilege("BLACKLIST_JWT");
        Privilege readUsersPaginated = createPrivilege("READ_USERS");
        Privilege registerUsersPrivilege = createPrivilege("REGISTER_USERS");
        Privilege csrSignPrivilege = createPrivilege("SIGN_CSR");
        Privilege readCsrDetailsPrivilege = createPrivilege("READ_CSR_DETAILS");
        Privilege readCsrsPrivilege = createPrivilege("READ_CSRS");
        Privilege readCertificatesPrivilege = createPrivilege("READ_CERTIFICATES");
        Privilege addRealEstatesPrivilege = createPrivilege("ADD_REAL_ESTATE_TO_USER");
        Privilege readRealEstateDevicesPrivilege = createPrivilege("READ_REAL_ESTATE_DEVICES");
        Privilege configureDevicesPrivilege = createPrivilege("CONFIGURE_DEVICES");
        Privilege readAlarmsPrivilege = createPrivilege("READ_ALARMS");
        Privilege readLogsPrivilege = createPrivilege("READ_LOGS");
        Privilege acknowledgeAlarmsPrivilege = createPrivilege("ACKNOWLEDGE_ALARMS");
        Privilege manageAlarmRulesPrivilege = createPrivilege("MANAGE_ALARM_RULES");

        // CREATE ROLES HERE...
        // Higher priority means that the role is more important
        // this is used when we look for possible user to assigned them to objects
        Role superAdminRole = createRole("ROLE_SUPER_ADMIN", 1000,
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
                readCsrsPrivilege,
                addRealEstatesPrivilege,
                readRealEstateDevicesPrivilege,
                configureDevicesPrivilege,
                readAlarmsPrivilege,
                readLogsPrivilege,
                acknowledgeAlarmsPrivilege,
                manageAlarmRulesPrivilege
        );
        Role adminRole = createRole("ROLE_ADMIN", 100,
                createRealEstatePrivilege,
                readMyRealEstatesPrivilege,
                deleteUsersPrivilege,
                modifyRolePrivilege,
                readUsersPaginated,
                registerUsersPrivilege,
                csrSignPrivilege,
                readCsrDetailsPrivilege,
                readCertificatesPrivilege,
                readCsrsPrivilege,
                addRealEstatesPrivilege,
                readRealEstateDevicesPrivilege,
                configureDevicesPrivilege,
                readAlarmsPrivilege,
                readLogsPrivilege
        );
        Role ownerRole = createRole("ROLE_OWNER", 99,
                createRealEstatePrivilege,
                readMyRealEstatesPrivilege,
                modifyRolePrivilege,
                readUsersPaginated,
                readRealEstateDevicesPrivilege
        );
        Role tenantRole = createRole("ROLE_TENANT", 98,
                readMyRealEstatesPrivilege,
                readUsersPaginated
        );
        roleRepository.saveAll(List.of(
                superAdminRole, adminRole, ownerRole, tenantRole
        ));

        // CREATE USERS HERE...
        User user1 = createUser("Pera", "Peric", "pera", "Test$123", superAdminRole);
        User user2 = createUser("Manji", "Pera", "manji_pera", "Test$123", adminRole);
        User user3 = createUser("Jos manji", "Pera", "jos_manji_pera", "Test$123", ownerRole);
        User user4 = createUser("Najmanji", "Pera", "najmanji_pera", "Test$123", tenantRole);
        User hajduk = createUser("Hajduk", "Dusan", "hajduk", "Hajduk1!", adminRole);


        // CREATE REAL ESTATES HERE...
        RealEstate home = new RealEstate("Kuca").addStakeholder(user1);
        RealEstate dogHouse = new RealEstate("Kuca za kera").addStakeholder(user1).addStakeholder(user4);
        RealEstate helperObject = new RealEstate("Pomocni objekat").addStakeholder(user1);

        user1.addRealEstate(home).addRealEstate(dogHouse).addRealEstate(helperObject);
        user4.addRealEstate(dogHouse);

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
        Privilege privilege = new Privilege();
        privilege.setName(name);
        return privilegeRepository.save(privilege);
    }

    private Role createRole(String name, Integer priority, Privilege... privileges) {
        Role role = new Role();
        role.setName(name);
        role.setPriority(priority);
        role.setPrivileges(Set.of(privileges));
        for (Privilege p : privileges) {
            Set<Role> roles = Objects.requireNonNullElse(p.getRoles(), new HashSet<Role>());
            roles.add(role);
            p.setRoles(roles);
        }
        return role;
    }
}
