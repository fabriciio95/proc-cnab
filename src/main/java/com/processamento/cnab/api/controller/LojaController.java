package com.processamento.cnab.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.processamento.cnab.domain.service.LojaService;
import com.processamento.cnab.dto.LojaDTO;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/lojas", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class LojaController {

	@Autowired
	private LojaService lojaService;
	
	@GetMapping
	public List<LojaDTO> findAllLojas() {
		return lojaService.findAllLojasTransacoes();
	}
}
