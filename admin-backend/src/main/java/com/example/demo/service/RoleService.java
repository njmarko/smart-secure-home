package com.example.demo.service;

import com.example.demo.model.Role;

import java.util.List;

public interface RoleService {
	Role findById(Long id);
	List<Role> findByName(String name);
}
