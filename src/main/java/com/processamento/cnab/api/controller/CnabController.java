package com.processamento.cnab.api.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.processamento.cnab.domain.service.ProcessadorCnabService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/cnabs", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Cnabs")
@AllArgsConstructor
public class CnabController {
	
	private ProcessadorCnabService processadorCnabService;
	
	@ApiOperation("Processa um arquivo cnab")
	@PostMapping(path = "/processamento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> processarCnab(@RequestPart(required = true) MultipartFile arquivo) throws IOException {
		
		try {
			
			processadorCnabService.processarCnab(arquivo.getResource());
			
			return ResponseEntity.noContent().build();
		}  catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	

}
