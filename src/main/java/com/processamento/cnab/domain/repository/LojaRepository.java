package com.processamento.cnab.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.processamento.cnab.domain.model.Loja;

@Repository
public interface LojaRepository extends JpaRepository<Loja, Long>{

	Optional<Loja> findByNome(String nome);
	
	@Query("select distinct l from Loja l join fetch l.transacoes order by l.nome")
	List<Loja> findAll();
}
