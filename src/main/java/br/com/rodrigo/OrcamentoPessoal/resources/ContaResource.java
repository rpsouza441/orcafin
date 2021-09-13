package br.com.rodrigo.OrcamentoPessoal.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.rodrigo.OrcamentoPessoal.domain.Conta;
import br.com.rodrigo.OrcamentoPessoal.dto.ContaDTO;
import br.com.rodrigo.OrcamentoPessoal.services.ContaService;

@RestController
@RequestMapping(value = "/conta")
public class ContaResource {
	
	@Autowired
	private ContaService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Conta obj = service.find(id);

		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping()
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> insert(@Valid @RequestBody ContaDTO objDTO) {
		Conta obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = gerarURI(obj);
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> put(@Valid @RequestBody ContaDTO dto, @PathVariable Integer id) {
		Conta obj = service.fromDTO(dto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping()
	public ResponseEntity<List<ContaDTO>> findAll() {
		List<Conta> list = service.findAll();
		List<ContaDTO> listDTO = list.stream().map(obj -> new ContaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(value = "/page")
	public ResponseEntity<Page<ContaDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Conta> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ContaDTO> listDTO = list.map(obj -> new ContaDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

	private URI gerarURI(Conta obj) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return uri;
	}

}
