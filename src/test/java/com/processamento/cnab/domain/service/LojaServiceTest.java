package com.processamento.cnab.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.processamento.cnab.domain.model.Loja;
import com.processamento.cnab.domain.model.Transacao;
import com.processamento.cnab.domain.model.enums.TipoTransacao;
import com.processamento.cnab.domain.repository.LojaRepository;
import com.processamento.cnab.dto.LojaDTO;

@ExtendWith(MockitoExtension.class)
class LojaServiceTest {
	
	@Mock
	private LojaRepository lojaRepository;
	
	@InjectMocks
	private LojaService lojaService;
	
	
	@Test
	void deveriaConsultarLojasETotalizarSaldoTotal() {
		when(lojaRepository.findAll()).thenReturn(Arrays.asList(criarLoja()));
		
		List<LojaDTO> lojas = lojaService.findAllLojasTransacoes();
		
		LojaDTO loja = lojas.get(0);
		
		assertEquals(loja.getTransacoes().size(), 2);
		assertEquals(loja.getSaldoTotal(), new BigDecimal("2000"));
		
	}
	
	private Loja criarLoja() {
		Loja loja = new Loja();
		loja.setNome("TESTE");
		loja.setProprietario("TESTE");
		
		Transacao transacao1 = new Transacao();
		transacao1.setCartao("31234");
		transacao1.setCpf("11111111111");
		transacao1.setDataOcorrencia(OffsetDateTime.now());
		transacao1.setId(1L);
		transacao1.setTipo(TipoTransacao.ALUGUEL_SAIDA);
		transacao1.setValor(new BigDecimal("1000"));
		transacao1.setLoja(loja);
		
		Transacao transacao2 = new Transacao();
		transacao2.setCartao("31234");
		transacao2.setCpf("11111111111");
		transacao2.setDataOcorrencia(OffsetDateTime.now());
		transacao2.setId(1L);
		transacao2.setTipo(TipoTransacao.CREDITO_ENTRADA);
		transacao2.setValor(new BigDecimal("3000"));
		transacao2.setLoja(loja);
		
		loja.setTransacoes(Arrays.asList(transacao1, transacao2));
		
		return loja;
		
	}

}
