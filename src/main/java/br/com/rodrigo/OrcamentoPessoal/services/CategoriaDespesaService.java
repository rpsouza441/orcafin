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
import br.com.rodrigo.OrcamentoPessoal.dto.CategoriaDespesaDTO;
import br.com.rodrigo.OrcamentoPessoal.repositories.CategoriaDespesaRepository;
import br.com.rodrigo.OrcamentoPessoal.services.exception.DataIntegrityException;
import br.com.rodrigo.OrcamentoPessoal.services.exception.ObjectNotFoundException;

@Service
public class CategoriaDespesaService {

	@Autowired
	private CategoriaDespesaRepository repo;
	
	
	public CategoriaDespesa find(Integer id) {
		Optional<CategoriaDespesa> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + CategoriaDespesa.class.getName()));
	}
	
	public CategoriaDespesa insert(CategoriaDespesa obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public CategoriaDespesa update(CategoriaDespesa obj) {
		CategoriaDespesa newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Categoria de Despesa que possui Despesas");
		}
	}

	public List<CategoriaDespesa> findAll() {
		return repo.findAll();
	}

	public Page<CategoriaDespesa> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest p = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(p);

	}
	
	public CategoriaDespesa fromDTO(CategoriaDespesaDTO dto) {
		return CategoriaDespesa.builder().id(dto.getId()).nome(dto.getNome()).build();
	}
	
	private void updateData(CategoriaDespesa newObj, CategoriaDespesa obj) {
		newObj.setNome(obj.getNome());
		
	}
}
