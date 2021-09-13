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

import br.com.rodrigo.OrcamentoPessoal.domain.CategoriaReceita;
import br.com.rodrigo.OrcamentoPessoal.dto.CategoriaReceitaDTO;
import br.com.rodrigo.OrcamentoPessoal.services.CategoriaReceitaService;

@RestController
@RequestMapping(value = "/categoriasReceita")
public class CategoriaReceitaResource {
	
	@Autowired
	private CategoriaReceitaService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		CategoriaReceita obj = service.find(id);

		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping()
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaReceitaDTO objDTO) {
		CategoriaReceita obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = gerarURI(obj);
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> put(@Valid @RequestBody CategoriaReceitaDTO dto, @PathVariable Integer id) {
		CategoriaReceita obj = service.fromDTO(dto);
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
	public ResponseEntity<List<CategoriaReceitaDTO>> findAll() {
		List<CategoriaReceita> list = service.findAll();
		List<CategoriaReceitaDTO> listDTO = list.stream().map(obj -> new CategoriaReceitaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(value = "/page")
	public ResponseEntity<Page<CategoriaReceitaDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<CategoriaReceita> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaReceitaDTO> listDTO = list.map(obj -> new CategoriaReceitaDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

	private URI gerarURI(CategoriaReceita obj) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return uri;
	}

}
