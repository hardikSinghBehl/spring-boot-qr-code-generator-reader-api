package com.hardik.iris.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@JacksonStdImpl
@NoArgsConstructor
@AllArgsConstructor
public class QrCodeGenerationRequestDto {

	@Schema(description = "Title of your message", required = true, example = "Important message")
	@NotBlank(message = "title must not be empty")
	@Size(max = 50, message = "Title should not exceed more than 50 characters")
	private String title;

	@Schema(description = "Message you want to decode", required = true, example = "Drink water")
	@NotBlank(message = "message must not be empty")
	@Size(max = 5000, message = "message should not exceed more than 5000 characters")
	private String message;

	@Schema(description = "Name of person generating the QR code ", required = true, example = "Hardik Singh Behl")
	@NotBlank(message = "generated-by-name must not be empty")
	private String generatedByName;

	@Schema(description = "Name of person(s) for which code is being generated", required = true, example = "For Whole World")
	@NotBlank(message = "generated-for-name must not be empty")
	private String generatedForName;

}
