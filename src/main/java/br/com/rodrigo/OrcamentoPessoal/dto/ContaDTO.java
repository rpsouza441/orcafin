package br.com.rodrigo.OrcamentoPessoal.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;

import br.com.rodrigo.OrcamentoPessoal.domain.Conta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String descricao;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String instituicaoFinanceira;

	@NotEmpty(message = "Preenchimento obrigatório")
	private Integer tipo;

	private Integer userID;
	
	private BigDecimal saldo;

	public ContaDTO(Conta obj) {
		this.id = obj.getId();
		this.descricao = obj.getDescricao();
		this.tipo = obj.getTipo().getCod();
		this.userID = obj.getUser().getId();
		this.saldo=obj.getSaldo();
	}
}
