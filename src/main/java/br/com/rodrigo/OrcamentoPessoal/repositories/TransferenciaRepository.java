package br.com.rodrigo.OrcamentoPessoal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rodrigo.OrcamentoPessoal.domain.Transferencia;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Integer>{


}
