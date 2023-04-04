package com.processamento.cnab.core.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.processamento.cnab.domain.model.Loja;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LojaDTO {

	private Long id;

	private String nome;

	private String proprietario;
	
	private BigDecimal saldoTotal;

	private List<TransacaoDTO> transacoes;
	
	
	public LojaDTO (Loja entidade) {
		this.id = entidade.getId();
		this.nome = entidade.getNome();
		this.proprietario = entidade.getProprietario();
		this.transacoes = entidade.getTransacoes().stream().map(TransacaoDTO::new).collect(Collectors.toList());
	}
}
