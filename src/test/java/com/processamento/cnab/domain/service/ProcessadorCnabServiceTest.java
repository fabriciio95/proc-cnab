package com.processamento.cnab.domain.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.processamento.cnab.domain.model.Loja;
import com.processamento.cnab.domain.model.Transacao;
import com.processamento.cnab.domain.repository.LojaRepository;
import com.processamento.cnab.domain.repository.TransacaoRepository;

class ProcessadorCnabServiceTest {
	
	@Mock
	private LojaRepository lojaRepository;
	
	@Mock
	private TransacaoRepository transacaoRepository;
	
	@Captor
	private ArgumentCaptor<List<Transacao>> captorListTransacao;
	
	private ProcessadorCnabService processadorCnabService;
	
	@BeforeEach
	void start() {
		
		MockitoAnnotations.initMocks(this);
		
		processadorCnabService = new ProcessadorCnabService();
		
		processadorCnabService.setLojaRepository(lojaRepository);
		
		processadorCnabService.setTransacaoRepository(transacaoRepository);
	}

	@Test
	void deveriaSalvarLojaETransacoes() {
		
		var linha = "3201903010000014200096206760174753****3153153453JOÃO MACEDO   BAR DO JOÃO       ";
				
		assertDoesNotThrow(() -> processadorCnabService.processarCnab(linha));
		
		verify(lojaRepository).save(any());
		
		verify(transacaoRepository).saveAll(anyIterable());
	}
	
	@Test
	void deveriaSalvarTransacoesDeUmaLojaJaExistente() {
		
		when(lojaRepository.findByNome(anyString())).thenReturn(Optional.of(new Loja("BAR DO JOÃO", "JOÃO MACEDO")));
		
		var linha = "3201903010000014200096206760174753****3153153453JOÃO MACEDO   BAR DO JOÃO       ";
				
		assertDoesNotThrow(() -> processadorCnabService.processarCnab(linha));
		
		verify(lojaRepository, times(0)).save(any());
		
		verify(transacaoRepository).saveAll(anyIterable());
	}
	
	@Test
	void deveriaProcessarCnabCorretamente() {
		
		when(lojaRepository.findByNome(anyString())).thenReturn(Optional.of(new Loja("BAR DO JOÃO", "JOÃO MACEDO")));
				
		var linha = "3201903010000014200096206760174753****3153153453JOÃO MACEDO   BAR DO JOÃO       ";
				
		assertDoesNotThrow(() -> processadorCnabService.processarCnab(linha));
				
		verify(transacaoRepository).saveAll(captorListTransacao.capture());
		
		Transacao transacao = captorListTransacao.getValue().get(0);
		
		assertEquals(transacao.getTipo().getTipo(), 3);
		assertEquals(transacao.getDataOcorrencia().toString(), "2019-03-01T15:34:53-03:00");
		assertEquals(transacao.getValor(), new BigDecimal("142"));
		assertEquals(transacao.getCpf(), "09620676017");
		assertEquals(transacao.getCartao(), "4753****3153");
		assertEquals(transacao.getLoja().getNome(), "BAR DO JOÃO");
		assertEquals(transacao.getLoja().getProprietario(), "JOÃO MACEDO");
	}
	
	@Test
	void deveriaLerCnabCorretamente() {
		
		Resource cnab = new ClassPathResource("files/cnab.txt");
		
		assertDoesNotThrow(() -> processadorCnabService.processarCnab(cnab));
		
		verify(transacaoRepository, atLeastOnce()).saveAll(anyIterable());
	}

}
