package com.example.demo.service;

import com.example.demo.dto.UserRequest;
import com.example.demo.model.User;

import java.util.List;

public interface UserService {
    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll ();
	User save(UserRequest userRequest);
}
