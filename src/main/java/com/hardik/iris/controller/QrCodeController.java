package com.hardik.iris.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.hardik.iris.dto.QrCodeGenerationRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class QrCodeController {

	@PostMapping(value = "/generate")
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "Returns a .png QR code with provided information decoded inside")
	public void qrCodeGenerationHandler(
			@RequestBody(required = true) final QrCodeGenerationRequestDto qrCodeGenerationRequestDto,
			final HttpServletResponse httpServletResponse) throws IOException, WriterException {

		httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION,
				"attachment;filename=" + qrCodeGenerationRequestDto.getTitle() + ".png");

		final var outputStream = new BufferedOutputStream(httpServletResponse.getOutputStream());
		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix bitMatrix = writer.encode(new ObjectMapper().writeValueAsString(qrCodeGenerationRequestDto),
				BarcodeFormat.QR_CODE, 350, 350);
		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
		outputStream.flush();
	}

	@PutMapping(value = "/read", consumes = "multipart/form-data")
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "returns decoded information inside provided QR code")
	public ResponseEntity<?> read(
			@Parameter(description = ".png image of QR code generated through this portal") @RequestParam(value = "file", required = true) MultipartFile file)
			throws IOException, NotFoundException {
		BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
		LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
		Result result = new MultiFormatReader().decode(binaryBitmap);
		return ResponseEntity.ok(new ObjectMapper().readValue(result.getText(), QrCodeGenerationRequestDto.class));
	}

}
