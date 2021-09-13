package br.com.rodrigo.OrcamentoPessoal.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CredenciaisDTO  implements Serializable{
	private static final long serialVersionUID = 1L;

	private String email;
	private String senha;

}
