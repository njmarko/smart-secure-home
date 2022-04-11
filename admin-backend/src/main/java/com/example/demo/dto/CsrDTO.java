package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CsrDTO {
	private String country;
	private String state;
	private String locality;
	private String organization;
	private String organizationalUnit;
	private String commonName;
	private Integer keySize;
}
