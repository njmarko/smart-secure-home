package com.example.demo.repository;

import com.example.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
	List<Role> findByName(String name);

	@Query("select r from Role r where r.priority < :priority")
	List<Role> findBellowPriority(Integer priority);
}
