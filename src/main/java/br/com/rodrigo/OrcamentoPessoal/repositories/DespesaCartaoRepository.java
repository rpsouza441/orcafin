package br.com.rodrigo.OrcamentoPessoal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rodrigo.OrcamentoPessoal.domain.DespesaCartao;

@Repository
public interface DespesaCartaoRepository extends JpaRepository<DespesaCartao, Integer>{


}
