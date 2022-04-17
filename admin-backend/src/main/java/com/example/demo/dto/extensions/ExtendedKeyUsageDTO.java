package com.example.demo.dto.extensions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.KeyPurposeId;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExtendedKeyUsageDTO extends ExtensionDTO {
	private List<ExtendedKeyUsageEnumDTO> keyUsages;

	public KeyPurposeId[] toKeyPurposeIds() {
		Map<ExtendedKeyUsageEnumDTO, String> hashMap = new HashMap<>();
		hashMap.put(ExtendedKeyUsageEnumDTO.TLS_WEB_CLIENT_AUTHENTICATION, "1.3.6.1.5.5.7.3.2");
		hashMap.put(ExtendedKeyUsageEnumDTO.TLS_WEB_SERVER_AUTHENTICATION, "1.3.6.1.5.5.7.3.1");
		hashMap.put(ExtendedKeyUsageEnumDTO.CODE_SIGNING, "1.3.6.1.5.5.7.3.3");
		return keyUsages.stream().map(hashMap::get)
				.map(ASN1ObjectIdentifier::new)
				.map(KeyPurposeId::getInstance)
				.toArray(KeyPurposeId[]::new);
	}
}
