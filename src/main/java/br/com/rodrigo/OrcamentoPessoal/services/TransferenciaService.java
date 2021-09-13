package br.com.rodrigo.OrcamentoPessoal.services;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.rodrigo.OrcamentoPessoal.domain.Conta;
import br.com.rodrigo.OrcamentoPessoal.domain.Transferencia;
import br.com.rodrigo.OrcamentoPessoal.dto.TransferenciaDTO;
import br.com.rodrigo.OrcamentoPessoal.repositories.ContaRepository;
import br.com.rodrigo.OrcamentoPessoal.repositories.TransferenciaRepository;
import br.com.rodrigo.OrcamentoPessoal.services.exception.DataIntegrityException;
import br.com.rodrigo.OrcamentoPessoal.services.exception.ObjectNotFoundException;

@Service
public class TransferenciaService {

	@Autowired
	private TransferenciaRepository repo;

	@Autowired
	private ContaService contaService;
	
	@Autowired
	private ContaRepository contaRepo;

	public Transferencia find(Integer id) {
		Optional<Transferencia> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Transferencia.class.getName()));
	}

	public Transferencia insert(Transferencia obj) {
		obj.setId(null);
		Conta origem = contaService.find(obj.getContaOrigem().getId());
		Conta destino = contaService.find(obj.getContaDestino().getId());
		transferirValores(origem, destino, obj.getValor());
		System.out.println(origem.getSaldo());
		contaRepo.saveAll(Arrays.asList(origem, destino));
		return repo.save(obj);
	}

	public Transferencia update(Transferencia obj) {
		Transferencia newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Tranferência que possui entidade");
		}
	}

	public List<Transferencia> findAll() {
		return repo.findAll();
	}

	public Page<Transferencia> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest p = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(p);

	}

	private void transferirValores(Conta origem, Conta destino, BigDecimal valor) {
		System.out.println("iniciando transferencia");
		origem.retirarSaldo(valor);
		destino.adicionarSaldo(valor);

	}

	public Transferencia fromDTO(TransferenciaDTO dto) {

		return Transferencia.builder()
				.id((dto.getId() == null) ? null : dto.getId())
				.data(dto.getData())
				.valor(dto.getValor())
				.contaOrigem(Conta.builder().id(dto.getContaOrigemID()).build())
				.contaDestino(Conta.builder().id(dto.getContaDestinoID()).build())
				.build();
	}

	private void updateData(Transferencia newObj, Transferencia obj) {
		newObj.setData(obj.getData());
		newObj.setValor(obj.getValor());
		newObj.setContaDestino(obj.getContaDestino());
		newObj.setContaOrigem(obj.getContaOrigem());
	}
}
