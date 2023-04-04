package com.processamento.cnab.domain.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Loja {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	private String nome;
	
	private String proprietario;
	
	@OneToMany(mappedBy = "loja")
	private List<Transacao> transacoes;
	
	public Loja() {}

	public Loja(String nome, String proprietario) {
		this.nome = nome;
		this.proprietario = proprietario;
	}
}
