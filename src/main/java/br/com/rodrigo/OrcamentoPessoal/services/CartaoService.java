package br.com.rodrigo.OrcamentoPessoal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.rodrigo.OrcamentoPessoal.domain.Cartao;
import br.com.rodrigo.OrcamentoPessoal.domain.enums.TipoBandeira;
import br.com.rodrigo.OrcamentoPessoal.dto.CartaoDTO;
import br.com.rodrigo.OrcamentoPessoal.repositories.CartaoRepository;
import br.com.rodrigo.OrcamentoPessoal.services.exception.DataIntegrityException;
import br.com.rodrigo.OrcamentoPessoal.services.exception.ObjectNotFoundException;

@Service
public class CartaoService {

	@Autowired
	private CartaoRepository repo;

	public Cartao find(Integer id) {
		Optional<Cartao> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cartao.class.getName()));
	}

	public Cartao insert(Cartao obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Cartao update(Cartao obj) {
		Cartao newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Cartão que possui despesas");
		}
	}

	public List<Cartao> findAll() {
		return repo.findAll();
	}

	public Page<Cartao> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest p = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(p);

	}

	public Cartao fromDTO(CartaoDTO dto) {
		return Cartao.builder().
				id((dto.getId() == null) ? null : dto.getId())
				.descricao(dto.getDescricao())
				.bandeira(TipoBandeira.toEnum(dto.getBandeira()))
				.limite(dto.getLimite())
				.diaFechamento(dto.getDiaFechamento())
				.diaVencimento(dto.getDiaVencimento()).build();
	}

	private void updateData(Cartao newObj, Cartao obj) {
		newObj.setDescricao(obj.getDescricao());
		newObj.setBandeira(obj.getBandeira());
		newObj.setLimite(obj.getLimite());
		newObj.setDiaFechamento(obj.getDiaFechamento());
		newObj.setDiaVencimento(obj.getDiaVencimento());

	}
}
