package br.com.rodrigo.OrcamentoPessoal.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import br.com.rodrigo.OrcamentoPessoal.domain.Receita;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceitaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String descricao;

	@NotEmpty(message = "Preenchimento obrigatório")
	private Date dataLancamento;
	
	private Date dataVencimento;

	private Date dataRecebimento;

	private boolean foiRecebido = false;

	@NotEmpty(message = "Preenchimento obrigatório")
	private BigDecimal Valor;

	@NotEmpty(message = "Preenchimento obrigatório")
	private Integer categoriaReceitaID;

	@NotEmpty(message = "Preenchimento obrigatório")
	private Integer contaID;

	public ReceitaDTO(Receita obj) {
		this.id = obj.getId();
		this.descricao = obj.getDescricao();
		this.dataLancamento = obj.getDataLancamento();
		this.dataVencimento= obj.getDataVencimento();
		this.dataRecebimento = obj.getDataRecebimento();
		this.foiRecebido = obj.isFoiRecebido();
		this.Valor = obj.getValor();
		this.categoriaReceitaID = obj.getCategoriaReceita().getId();
		this.contaID = obj.getConta().getId();
	}
}
