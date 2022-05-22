package com.example.demo.service.impl;

import com.example.demo.dto.UserRequest;
import com.example.demo.model.BaseEntity;
import com.example.demo.model.RealEstate;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RealEstateRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
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
		
		u.setFirstName(userRequest.getFirstname());
		u.setLastName(userRequest.getLastname());
		u.setEnabled(true);
		u.setEmail(userRequest.getEmail());

		// u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
		List<Role> roles = roleService.findByName("ROLE_USER");
		u.setRoles(roles);
		
		return this.userRepository.save(u);
	}

	private boolean isAdminUser(User user) {
		var adminRoles = Set.of("ROLE_ADMIN", "ROLE_SUPER_ADMIN");
		return user.getRoles().stream().map(Role::getName).anyMatch(adminRoles::contains);
	}

}
