package br.com.rodrigo.OrcamentoPessoal.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import br.com.rodrigo.OrcamentoPessoal.domain.Cartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;

	private String descricao;

	@NotEmpty(message = "Preenchimento obrigatório")
	private Integer bandeira;

	@NotEmpty(message = "Preenchimento obrigatório")
	private BigDecimal limite;

	@NotEmpty(message = "Preenchimento obrigatório")
	private Integer diaFechamento;

	@NotEmpty(message = "Preenchimento obrigatório")
	private Integer diaVencimento;

	@NotEmpty(message = "Preenchimento obrigatório")
	private Integer contaParaDebitarId;

	public CartaoDTO(Cartao obj) {
		this.id = obj.getId();
		this.descricao = obj.getDescricao();
		this.bandeira = obj.getBandeira().getCod();
		this.limite = obj.getLimite();
		this.diaFechamento = obj.getDiaFechamento();
		this.diaVencimento = obj.getDiaVencimento();
		this.contaParaDebitarId = obj.getContaParaDebitar().getId();
	}
}
