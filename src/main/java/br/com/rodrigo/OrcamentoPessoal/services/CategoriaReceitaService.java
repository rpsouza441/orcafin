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
import br.com.rodrigo.OrcamentoPessoal.dto.CategoriaReceitaDTO;
import br.com.rodrigo.OrcamentoPessoal.repositories.CategoriaReceitaRepository;
import br.com.rodrigo.OrcamentoPessoal.services.exception.DataIntegrityException;
import br.com.rodrigo.OrcamentoPessoal.services.exception.ObjectNotFoundException;

@Service
public class CategoriaReceitaService {

	@Autowired
	private CategoriaReceitaRepository repo;
	
	
	public CategoriaReceita find(Integer id) {
		Optional<CategoriaReceita> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + CategoriaReceita.class.getName()));
	}
	
	public CategoriaReceita insert(CategoriaReceita obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public CategoriaReceita update(CategoriaReceita obj) {
		CategoriaReceita newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Categoria de Receita que possui Receitas");
		}
	}

	public List<CategoriaReceita> findAll() {
		return repo.findAll();
	}

	public Page<CategoriaReceita> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest p = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(p);

	}
	
	public CategoriaReceita fromDTO(CategoriaReceitaDTO dto) {
		return CategoriaReceita.builder().id(dto.getId()).nome(dto.getNome()).build();
	}
	
	private void updateData(CategoriaReceita newObj, CategoriaReceita obj) {
		newObj.setNome(obj.getNome());
		
	}
}
