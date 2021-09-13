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

import br.com.rodrigo.OrcamentoPessoal.domain.Transferencia;
import br.com.rodrigo.OrcamentoPessoal.dto.TransferenciaDTO;
import br.com.rodrigo.OrcamentoPessoal.services.TransferenciaService;

@RestController
@RequestMapping(value = "/transferencia")
public class TransferenciaResource {
	
	@Autowired
	private TransferenciaService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Transferencia obj = service.find(id);

		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping()
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> insert(@Valid @RequestBody TransferenciaDTO objDTO) {
		Transferencia obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = gerarURI(obj);
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> put(@Valid @RequestBody TransferenciaDTO dto, @PathVariable Integer id) {
		Transferencia obj = service.fromDTO(dto);
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
	public ResponseEntity<List<TransferenciaDTO>> findAll() {
		List<Transferencia> list = service.findAll();
		List<TransferenciaDTO> listDTO = list.stream().map(obj -> new TransferenciaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(value = "/page")
	public ResponseEntity<Page<TransferenciaDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Transferencia> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<TransferenciaDTO> listDTO = list.map(obj -> new TransferenciaDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

	private URI gerarURI(Transferencia obj) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return uri;
	}

}
