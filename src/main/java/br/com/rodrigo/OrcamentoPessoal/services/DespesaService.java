package br.com.rodrigo.OrcamentoPessoal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.rodrigo.OrcamentoPessoal.domain.CategoriaDespesa;
import br.com.rodrigo.OrcamentoPessoal.domain.Conta;
import br.com.rodrigo.OrcamentoPessoal.domain.Despesa;
import br.com.rodrigo.OrcamentoPessoal.dto.DespesaDTO;
import br.com.rodrigo.OrcamentoPessoal.repositories.DespesaRepository;
import br.com.rodrigo.OrcamentoPessoal.services.exception.DataIntegrityException;
import br.com.rodrigo.OrcamentoPessoal.services.exception.ObjectNotFoundException;

@Service
public class DespesaService {

	@Autowired
	private DespesaRepository repo;

	public Despesa find(Integer id) {
		Optional<Despesa> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Despesa.class.getName()));
	}

	public Despesa insert(Despesa obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Despesa update(Despesa obj) {
		Despesa newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Despesa que possui entidade");
		}
	}

	public List<Despesa> findAll() {
		return repo.findAll();
	}

	public Page<Despesa> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest p = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(p);

	}

	public Despesa fromDTO(DespesaDTO dto) {
		return Despesa.builder().
				id((dto.getId() == null) ? null : dto.getId())
				.descricao(dto.getDescricao())
				.dataLancamento(dto.getDataLancamento())
				.dataVencimento(dto.getDataVencimento())
				.dataPagamento(dto.getDataPagamento())
				.estaPago(dto.isEstaPago())
				.valor(dto.getValor())
				.categoriaDespesa(CategoriaDespesa.builder().id(dto.getCategoriaDespesaID()).build())
				.conta(Conta.builder().id(dto.getContaID()).build())
				.build();
	}

	private void updateData(Despesa newObj, Despesa obj) {
		newObj.setDescricao(obj.getDescricao());
		newObj.setDataLancamento(obj.getDataLancamento());
		newObj.setDataVencimento(obj.getDataVencimento());
		newObj.setDataPagamento(obj.getDataPagamento());
		newObj.setEstaPago(obj.isEstaPago());
		newObj.setValor(obj.getValor());
		newObj.setCategoriaDespesa(obj.getCategoriaDespesa());
		newObj.setConta(obj.getConta());

	}
}
