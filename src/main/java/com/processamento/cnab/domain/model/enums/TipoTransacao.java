package com.processamento.cnab.domain.model.enums;

import lombok.Getter;

@Getter
public enum TipoTransacao {

	DEBITO_ENTRADA(1, "Débito", "Entrada", "+"),
	BOLETO_SAIDA(2,"Boleto", "Saída","-"),
	FINANCIAMENTO_SAIDA(3,"Financiamento", "Saída", "-"),
	CREDITO_ENTRADA(4, "Crédito", "Entrada", "+"),
	RECEBIMENTO_EMPRESTIMO(5, "Recebimento Empréstimo", "Entrada", "+"),
	VENDAS_ENTRADA(6, "Vendas", "Entrada", "+"),
	RECEBIMENTO_TED_ENTRADA(7, "Recebimento TED", "Entrada", "+"),
	RECEBIMENTO_DOC(8, "Recebimento DOC", "Entrada", "+"),
	ALUGUEL_SAIDA(9, "Aluguel", "Saída", "-");
	
	private int tipo;
	private String descricao;
	private String natureza;
	private String sinal;
	
	private TipoTransacao(int tipo, String descricao, String natureza, String sinal) {
		this.tipo = tipo;
		this.descricao = descricao;
		this.natureza = natureza;
		this.sinal = sinal;
	}
	
	public static TipoTransacao obterTransacaoPeloTipo(String tipo) {
		for(TipoTransacao tipoTransacao : values()) {
			if(tipoTransacao.getTipo() == Integer.valueOf(tipo)) {
				return tipoTransacao;
			}
		}
		
		return null;
	}
}
