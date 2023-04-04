package com.processamento.cnab.domain.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.processamento.cnab.core.dto.LojaDTO;
import com.processamento.cnab.domain.model.Loja;
import com.processamento.cnab.domain.model.Transacao;
import com.processamento.cnab.domain.repository.LojaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LojaService {

	private LojaRepository lojaRepository;
	
	public List<LojaDTO> findAllLojasTransacoes() {
		List<Loja> lojas = lojaRepository.findAll();
		 
		List<LojaDTO> lojasDTO = new ArrayList<>();
		
		lojas.forEach(loja -> {
			LojaDTO lojaDTO = new LojaDTO(loja);
		    
			BigDecimal totalValorEntradas = loja.getTransacoes().stream()
		    		.filter(t -> t.getTipo().getSinal().equals("+"))
					.map(Transacao::getValor)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
		    
		    BigDecimal totalValorSaidas =  loja.getTransacoes().stream()
		    		.filter(t -> t.getTipo().getSinal().equals("-"))
					.map(Transacao::getValor)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
		    
		    lojaDTO.setSaldoTotal(totalValorEntradas.subtract(totalValorSaidas));
		    
			lojasDTO.add(lojaDTO);
		});
		
		return lojasDTO;
	}
}
