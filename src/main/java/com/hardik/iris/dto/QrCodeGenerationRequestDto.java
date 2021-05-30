package com.hardik.iris.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class QrCodeGenerationRequestDto {

	private String title;
	private String message;
	private String generatedByName;
	private String generatedForName;

}
