package com.example.demo.dto.extensions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KeyUsageDTO extends ExtensionDTO {
	private List<KeyUsageEnumDTO> keyUsages;
}
