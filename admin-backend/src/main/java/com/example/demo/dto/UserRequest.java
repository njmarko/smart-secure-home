package com.example.demo.dto;

import com.example.demo.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

// DTO koji preuzima podatke iz HTML forme za registraciju
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

	private Long id;

	@NotBlank(message = "Username is required.")
	@Pattern(regexp = "^(?=[a-zA-Z0-9._]{4,20}$)(?!.*[_.]{2})[^_.].*[^_.]$")
	private String username;

	@NotBlank(message = "Password is required.")
	@Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-zd$@$!%*?&].{7,50}")
	private String password;

	@NotBlank(message = "First name is required.")
	@Length(max = 100, message = "First name cannot be longer than 100 characters.")
	private String firstName;

	@NotBlank(message = "Last name is required.")
	@Length(max = 100, message = "Last name cannot be longer than 100 characters.")
	private String lastName;

	@NotBlank(message = "Email is required.")
	@Email(message = "Email must be valid.")
	private String email;

	@NotBlank(message = "Role is required.")
	@Pattern(regexp = "[a-zA-Z]+_[a-zA-Z]+")
	@Length(max = 50, message = "Role cannot be longer than 50 characters.")
	private String role;


}
