package com.processamento.cnab.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.processamento.cnab.domain.model.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

}
