package com.example.demo.dto.extensions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KeyUsageDTO extends ExtensionDTO {
	private List<KeyUsageEnumDTO> keyUsages;

	public int toBitMask() {
		Map<KeyUsageEnumDTO, Integer> hashMap = new HashMap<>();
		hashMap.put(KeyUsageEnumDTO.KEY_ENCIPHERMENT, 32);		// 5
		hashMap.put(KeyUsageEnumDTO.CRL_SIGN, 2);				// 1
		hashMap.put(KeyUsageEnumDTO.DIGITAL_SIGNATURE, 128); 	// 7
		hashMap.put(KeyUsageEnumDTO.CERTIFICATE_SIGNING, 4); 	// 2
		return keyUsages.parallelStream()
				.map(hashMap::get)
				.map(x -> 1 << x)
				.reduce(0, (x, y) -> x | y);
	}
}
