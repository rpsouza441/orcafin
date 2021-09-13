package br.com.rodrigo.OrcamentoPessoal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.rodrigo.OrcamentoPessoal.domain.CategoriaReceita;
import br.com.rodrigo.OrcamentoPessoal.domain.Conta;
import br.com.rodrigo.OrcamentoPessoal.domain.Receita;
import br.com.rodrigo.OrcamentoPessoal.dto.ReceitaDTO;
import br.com.rodrigo.OrcamentoPessoal.repositories.ReceitaRepository;
import br.com.rodrigo.OrcamentoPessoal.services.exception.DataIntegrityException;
import br.com.rodrigo.OrcamentoPessoal.services.exception.ObjectNotFoundException;

@Service
public class ReceitaService {

	@Autowired
	private ReceitaRepository repo;

	public Receita find(Integer id) {
		Optional<Receita> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Receita.class.getName()));
	}

	public Receita insert(Receita obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Receita update(Receita obj) {
		Receita newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Receita que possui entidade");
		}
	}

	public List<Receita> findAll() {
		return repo.findAll();
	}

	public Page<Receita> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest p = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(p);

	}

	public Receita fromDTO(ReceitaDTO dto) {
		return Receita.builder().
				id((dto.getId() == null) ? null : dto.getId())
				.descricao(dto.getDescricao())
				.dataLancamento(dto.getDataLancamento())
				.dataVencimento(dto.getDataVencimento())
				.dataRecebimento(dto.getDataRecebimento())
				.foiRecebido(dto.isFoiRecebido())
				.valor(dto.getValor())
				.categoriaReceita(CategoriaReceita.builder().id(dto.getCategoriaReceitaID()).build())
				.conta(Conta.builder().id(dto.getContaID()).build())
				.build();
	}

	private void updateData(Receita newObj, Receita obj) {
		newObj.setDescricao(obj.getDescricao());
		newObj.setDataLancamento(obj.getDataLancamento());
		newObj.setDataVencimento(obj.getDataVencimento());
		newObj.setDataRecebimento(obj.getDataRecebimento());
		newObj.setFoiRecebido(obj.isFoiRecebido());
		newObj.setValor(obj.getValor());
		newObj.setCategoriaReceita(obj.getCategoriaReceita());
		newObj.setConta(obj.getConta());

	}
}
