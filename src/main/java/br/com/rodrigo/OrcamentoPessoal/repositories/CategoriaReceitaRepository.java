package br.com.rodrigo.OrcamentoPessoal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rodrigo.OrcamentoPessoal.domain.CategoriaReceita;

@Repository
public interface CategoriaReceitaRepository extends JpaRepository<CategoriaReceita, Integer>{


}
