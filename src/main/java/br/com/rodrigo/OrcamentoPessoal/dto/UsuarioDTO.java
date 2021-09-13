package br.com.rodrigo.OrcamentoPessoal.dto;

import java.io.Serializable;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.rodrigo.OrcamentoPessoal.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;

	private String nome;

	@Column(unique = true)
	private String email;

	@JsonIgnore
	private String senha;

	public UsuarioDTO(Usuario obj) {
		this.nome = obj.getNome();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();

	}
}
