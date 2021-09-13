package br.com.rodrigo.OrcamentoPessoal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.rodrigo.OrcamentoPessoal.domain.Conta;
import br.com.rodrigo.OrcamentoPessoal.domain.enums.TipoConta;
import br.com.rodrigo.OrcamentoPessoal.dto.ContaDTO;
import br.com.rodrigo.OrcamentoPessoal.repositories.ContaRepository;
import br.com.rodrigo.OrcamentoPessoal.services.exception.DataIntegrityException;
import br.com.rodrigo.OrcamentoPessoal.services.exception.ObjectNotFoundException;

@Service
public class ContaService {

	@Autowired
	private ContaRepository repo;
	
	
	public Conta find(Integer id) {
		Optional<Conta> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Conta.class.getName()));
	}
	
	public Conta insert(Conta obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Conta update(Conta obj) {
		Conta newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Conta que possui Despesas ou Receitas");
		}
	}

	public List<Conta> findAll() {
		return repo.findAll();
	}

	public Page<Conta> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest p = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(p);

	}
	
	public Conta fromDTO(ContaDTO dto) {
		return Conta.builder().id(dto.getId())
				.descricao(dto.getDescricao())
				.instituicaoFinanceira(dto.getInstituicaoFinanceira())
				.saldo(dto.getSaldo())
				.tipo(TipoConta.toEnum(dto.getTipo()))
				.build();
	}
	
	private void updateData(Conta newObj, Conta obj) {
		newObj = Conta.builder()
				.id(obj.getId())
				.saldo(obj.getSaldo())
				.descricao(obj.getDescricao())
				.instituicaoFinanceira(obj.getInstituicaoFinanceira())
				.tipo(obj.getTipo())
				.user(obj.getUser())
				.listaReceitas(obj.getListaReceitas())
				.listaDespesas(obj.getListaDespesas())
				.listaTransfRealizadas(obj.getListaTransfRealizadas())
				.listaTransfRecebidas(obj.getListaTransfRecebidas())
				.build();
		
		
	}
}
