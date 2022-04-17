package com.example.demo.dto.extensions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExtensionsDTO {
	private AuthorityKeyIdentifierDTO authorityKeyIdentifier;
	private BasicConstraintsDTO basicConstraints;
	private ExtendedKeyUsageDTO extendedKeyUsage;
	private KeyUsageDTO keyUsage;
	private SubjectKeyIdentifierDTO subjectKeyIdentifier;
	private SubjectAlternativeNameDTO subjectAlternativeName;
}
