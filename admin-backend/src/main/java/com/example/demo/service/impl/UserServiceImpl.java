package com.example.demo.service.impl;

import com.example.demo.dto.UpdateUsersRealEstates;
import com.example.demo.dto.UserRequest;
import com.example.demo.exception.RealEstateNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.BaseEntity;
import com.example.demo.model.RealEstate;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RealEstateRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
	private static final Integer CONSECUTIVE_SIGN_IN_MISTAKES_LIMIT = 5;
	// TODO: Change this to smaller number if we are required to demonstrate this feature
	private static final long LOCK_DURATION_SECONDS = 15*60L;
	private final UserRepository userRepository;
	private final RealEstateRepository realEstateRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleService roleService;

	@Override
	public User findByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsernameAndIsActiveTrue(username);
	}

	public User findById(Integer id) throws AccessDeniedException {
		return userRepository.findById(id).orElseGet(null);
	}

	public List<User> findAll() throws AccessDeniedException {
		return userRepository.findAll();
	}

	@Override
	@Transactional
	public List<RealEstate> getMyRealEstates(String username) {
		var user = findByUsername(username);
		if (isAdminUser(user)) {
			return realEstateRepository.read();
		}
		return user.getRealEstates().stream().filter(BaseEntity::getIsActive).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public List<User> getUsersBellowMyRole(String username) {
		var user = findByUsername(username);
		var myRoleLevel = user.getRoles().stream().map(Role::getPriority).min(Integer::compareTo).orElse(0);
		var rolesBellowMine = roleService.getRolesBellowPriority(myRoleLevel);
		return findWithRoles(new HashSet<>(rolesBellowMine));
	}

	@Override
	@Transactional
	public void tryLockAccount(String username) {
		var user = userRepository.findByUsernameAndIsActiveTrue(username);
		if (Objects.isNull(user)) {
			return;
		}
		user.incorrectSignIn();
		if (user.getConsecutiveSignInMistakes() >= CONSECUTIVE_SIGN_IN_MISTAKES_LIMIT) {
			user.setConsecutiveSignInMistakes(0);
			user.setLockedUntil(Timestamp.valueOf(LocalDateTime.now().plusSeconds(LOCK_DURATION_SECONDS)));
			log.info(String.format("Banning user %s for %d seconds.", user, LOCK_DURATION_SECONDS));
		}
	}

	@Override
	public Page<User> getUsersBellowMyRolePaginated(String name, Pageable pageable) {
		var user = findByUsername(name);
		var myRoleLevel = user.getRoles().stream().map(Role::getPriority).min(Integer::compareTo).orElse(0);
		var rolesBellowMine = roleService.getRolesBellowPriority(myRoleLevel);
		return userRepository.findByIsActiveTrueAndRolesIn(rolesBellowMine, pageable);
	}

	@Override
	@Transactional
	public void updateRealEstates(Integer userId, UpdateUsersRealEstates usersRealEstates) {
		var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
		for (var realEstate: user.getRealEstates()) {
			realEstate.getStakeholders().remove(user);
		}
		user.setRealEstates(new HashSet<>());
		for (var id: usersRealEstates.getRealEstates()) {
			var realEstate = realEstateRepository.findById(id).orElseThrow(RealEstateNotFoundException::new);
			realEstate.addStakeholder(user);
			user.getRealEstates().add(realEstate);
		}
	}

	@Override
	public User getUserDetails(Integer id) {
		return userRepository.readWithRealEstates(id).orElseThrow(UserNotFoundException::new);
	}

	private List<User> findWithRoles(Set<Role> rolesBellowMine) {
		return userRepository.read().stream().filter(u -> u.getRoles().stream().anyMatch(rolesBellowMine::contains)).collect(Collectors.toList());
	}

	@Override
	public User save(UserRequest userRequest) {
		User u = new User();
		u.setUsername(userRequest.getUsername());
		
		// pre nego sto postavimo lozinku u atribut hesiramo je kako bi se u bazi nalazila hesirana lozinka
		// treba voditi racuna da se koristi isi password encoder bean koji je postavljen u AUthenticationManager-u kako bi koristili isti algoritam
		u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		
		u.setFirstName(userRequest.getFirstName());
		u.setLastName(userRequest.getLastName());
		u.setEnabled(true);
		u.setEmail(userRequest.getEmail());

		// u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
		List<Role> roles = roleService.findByName(userRequest.getRole());
		u.setRoles(roles);
		
		return this.userRepository.save(u);
	}

	@Override
	@Transactional
	public void deleteUser(String username, String issuerName){
		var myRoleLevel = userRepository.findByUsernameAndIsActiveTrue(issuerName).getRoles().stream().map(Role::getPriority).min(Integer::compareTo).orElse(0);
		var rolesBellowMine = roleService.getRolesBellowPriority(myRoleLevel);

		var user = userRepository.findByUsernameAndIsActiveTrueAndRolesIn(username, rolesBellowMine);

		// not authorized
		if (user == null){
			return;
		}

		user.setIsActive(false);
		userRepository.save(user);
	}

	@Override
	public void modifyRole(String username, String roleName, String issuerName) {
		var issuer = userRepository.findByUsernameAndIsActiveTrue(issuerName);
		var myRoleLevel = issuer.getRoles().stream().map(Role::getPriority).min(Integer::compareTo).orElse(0);
		var rolesBellowMine = roleService.getRolesBellowPriority(myRoleLevel);

		// provera da li rola koja se dodeljuje nije iznad role korisnika koji je pokrenuo akciju
		var role = roleService.findByNameAndPriorityLessThanEqual(roleName, myRoleLevel);
		
		// provera da li je korisnik kome se dodeljuje rola ispod korisnika koji je pokrenuo akciju
		var user = userRepository.findByUsernameAndIsActiveTrueAndRolesIn(username, rolesBellowMine);

		// not authorized
		if (user == null || role == null){
			return;
		}

		user.setRoles(List.of(role));
		
		userRepository.save(user);
	}

	private boolean isAdminUser(User user) {
		var adminRoles = Set.of("ROLE_ADMIN", "ROLE_SUPER_ADMIN");
		return user.getRoles().stream().map(Role::getName).anyMatch(adminRoles::contains);
	}

}
