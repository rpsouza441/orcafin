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
import br.com.rodrigo.OrcamentoPessoal.domain.CategoriaDespesa;
import br.com.rodrigo.OrcamentoPessoal.domain.DespesaCartao;
import br.com.rodrigo.OrcamentoPessoal.dto.DespesaCartaoDTO;
import br.com.rodrigo.OrcamentoPessoal.repositories.DespesaCartaoRepository;
import br.com.rodrigo.OrcamentoPessoal.services.exception.DataIntegrityException;
import br.com.rodrigo.OrcamentoPessoal.services.exception.ObjectNotFoundException;

@Service
public class DespesaCartaoService {

	@Autowired
	private DespesaCartaoRepository repo;

	public DespesaCartao find(Integer id) {
		Optional<DespesaCartao> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + DespesaCartao.class.getName()));
	}

	public DespesaCartao insert(DespesaCartao obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public DespesaCartao update(DespesaCartao obj) {
		DespesaCartao newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Despesa de Cartao que possui entidades");
		}
	}

	public List<DespesaCartao> findAll() {
		return repo.findAll();
	}

	public Page<DespesaCartao> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest p = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(p);

	}

	public DespesaCartao fromDTO(DespesaCartaoDTO dto) {
		return DespesaCartao.builder().
				id((dto.getId() == null) ? null : dto.getId())
				.descricao(dto.getDescricao())
				.dataLancamento(dto.getDataLancamento())
				.dataVencimento(dto.getDataVencimento())
				.dataPagamento(dto.getDataPagamento())
				.estaPago(dto.isEstaPago())
				.valor(dto.getValor())
				.categoriaDespesa(CategoriaDespesa.builder().id(dto.getCategoriaDespesaID()).build())
				.cartao(Cartao.builder().id(dto.getCartaoID()).build())
				.build();
	}

	private void updateData(DespesaCartao newObj, DespesaCartao obj) {
		newObj.setDescricao(obj.getDescricao());
		newObj.setDataLancamento(obj.getDataLancamento());
		newObj.setDataVencimento(obj.getDataVencimento());
		newObj.setDataPagamento(obj.getDataPagamento());
		newObj.setEstaPago(obj.isEstaPago());
		newObj.setValor(obj.getValor());
		newObj.setCategoriaDespesa(obj.getCategoriaDespesa());
		newObj.setCartao(obj.getCartao());

	}
}
