package br.com.rodrigo.OrcamentoPessoal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rodrigo.OrcamentoPessoal.domain.CategoriaDespesa;

@Repository
public interface CategoriaDespesaRepository extends JpaRepository<CategoriaDespesa, Integer>{


}
