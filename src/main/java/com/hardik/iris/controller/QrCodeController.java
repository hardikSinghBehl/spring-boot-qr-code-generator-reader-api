package com.hardik.iris.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.hardik.iris.dto.QrCodeGenerationRequestDto;
import com.hardik.iris.service.QrCodeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class QrCodeController {

	private final QrCodeService qrCodeService;

	@PostMapping(value = "/generate")
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "Returns a .png QR code with provided information decoded inside")
	public void qrCodeGenerationHandler(
			@RequestBody(required = true) final QrCodeGenerationRequestDto qrCodeGenerationRequestDto,
			final HttpServletResponse httpServletResponse) throws IOException, WriterException {
		qrCodeService.generate(qrCodeGenerationRequestDto, httpServletResponse);
	}

	@PutMapping(value = "/read", consumes = "multipart/form-data")
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "returns decoded information inside provided QR code")
	public ResponseEntity<?> read(
			@Parameter(description = ".png image of QR code generated through this portal") @RequestParam(value = "file", required = true) MultipartFile file)
			throws IOException, NotFoundException {
		return qrCodeService.read(file);
	}

}
