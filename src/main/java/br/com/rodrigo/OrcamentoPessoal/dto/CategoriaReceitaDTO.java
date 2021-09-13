package br.com.rodrigo.OrcamentoPessoal.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.rodrigo.OrcamentoPessoal.domain.CategoriaReceita;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaReceitaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
	private String nome;

	public CategoriaReceitaDTO(CategoriaReceita obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}
}
