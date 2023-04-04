package com.processamento.cnab.domain.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.processamento.cnab.domain.exceptions.ProcessamentoCnabException;
import com.processamento.cnab.domain.model.Loja;
import com.processamento.cnab.domain.model.Transacao;
import com.processamento.cnab.domain.model.enums.TipoTransacao;
import com.processamento.cnab.domain.repository.LojaRepository;
import com.processamento.cnab.domain.repository.TransacaoRepository;

import lombok.Setter;

@Service
@Setter
public class ProcessadorCnabService {
	
	@Autowired
	private LojaRepository lojaRepository;
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	public void processarCnab(Resource arquivo) throws IOException {
        try (InputStream inputStream = arquivo.getInputStream()) {
            String conteudo = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            
            processarCnab(conteudo);
        }
	}

	@Transactional
	public void processarCnab(String conteudoCnab) {
		
		List<Transacao> transacoesParaSalvar = new ArrayList<>();
		  
		try(Scanner scanner = new Scanner(conteudoCnab)) {

	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();
	            
	            Transacao transacao = new Transacao();
	            
	            DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
	            	            
	            transacao.setTipo(TipoTransacao.obterTransacaoPeloTipo(line.substring(0, 1)));
	            String data = line.substring(1, 9).trim();
	            String hora = line.substring(42, 48).trim();
	            transacao.setDataOcorrencia(LocalDateTime.parse(data + "T" + hora, dataFormato).atOffset(ZoneOffset.of("-03:00")));
	            
	            transacao.setValor(BigDecimal.valueOf(Long.parseLong(line.substring(9, 19)) / 100));
	            transacao.setCpf(line.substring(19, 30));
	            transacao.setCartao(line.substring(30, 42));
	            
	            String donoLoja = line.substring(48, 62).trim();
	            String nomeLoja = line.substring(62).trim();
	            transacao.setLoja(definirLoja(nomeLoja, donoLoja));
	            
	            transacoesParaSalvar.add(transacao);

	        }
	        
	        transacaoRepository.saveAll(transacoesParaSalvar);
	        
		  } catch(Exception e) {
			  throw new ProcessamentoCnabException("Erro ao processar cnab: " + e.getMessage(), e);
		  }		
	}

	@Transactional
	private Loja definirLoja(String nomeLoja, String donoLoja) {
		 Optional<Loja> lojaOpt = lojaRepository.findByNome(nomeLoja);
		 
		 Loja loja = null;
		 
		 if(lojaOpt.isEmpty()) {
			 loja = lojaRepository.save(new Loja(nomeLoja, donoLoja));
		 } else {
			 loja = lojaOpt.get();
		 }
		 
		 return loja;
	}
}
