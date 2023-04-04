package com.processamento.cnab.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.processamento.cnab.core.dto.LojaDTO;
import com.processamento.cnab.domain.service.LojaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/lojas", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Lojas")
@AllArgsConstructor
public class LojaController {

	@Autowired
	private LojaService lojaService;
	
	@ApiOperation("Lista as lojas e suas respectivas transações")
	@GetMapping
	public List<LojaDTO> findAllLojas() {
		return lojaService.findAllLojasTransacoes();
	}
}
