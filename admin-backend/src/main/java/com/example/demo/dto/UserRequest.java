package com.example.demo.dto;

import com.example.demo.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO koji preuzima podatke iz HTML forme za registraciju
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

	private Long id;

	private String username;

	private String password;

	private String firstName;

	private String lastName;
	
	private String email;

	private String role;


}
