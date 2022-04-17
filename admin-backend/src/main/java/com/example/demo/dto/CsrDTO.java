package com.example.demo.dto;

import com.example.demo.model.CSRPurpose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CsrDTO {
	private Integer id;
	private X500PrincipalData x500Name;
	private CSRPurpose purpose;
}
