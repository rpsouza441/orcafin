package br.com.rodrigo.OrcamentoPessoal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rodrigo.OrcamentoPessoal.domain.Receita;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Integer>{


}
