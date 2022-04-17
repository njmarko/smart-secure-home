package com.example.demo.dto.extensions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicConstraintsDTO extends ExtensionDTO {
	private Boolean subjectIsCa;
}
