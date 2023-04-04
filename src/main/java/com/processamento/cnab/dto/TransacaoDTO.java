package com.processamento.cnab.dto;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import com.processamento.cnab.domain.model.Transacao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoDTO {

    private Long id;
	
	private String tipo;
	
	private String dataOcorrencia;
	
	private BigDecimal valor;
	
	private String cpf;
	
	private String cartao;
	
	public TransacaoDTO(Transacao entidade) {
		this.id = entidade.getId();
		this.tipo = entidade.getTipo().getDescricao();
		this.dataOcorrencia = entidade.getDataOcorrencia().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		this.valor = entidade.getValor();
		this.cpf = entidade.getCpf();
		this.cartao = entidade.getCartao();
	}
}
