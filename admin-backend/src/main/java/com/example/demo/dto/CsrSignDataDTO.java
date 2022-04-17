package com.example.demo.dto;

import com.example.demo.dto.extensions.ExtensionsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CsrSignDataDTO {
	private CsrDTO csrDTO;
	private Date validityStart;
	private Date validityEnd;
	private SignatureAlgEnumDTO signatureAlg;
	private ExtensionsDTO extensions;

}
