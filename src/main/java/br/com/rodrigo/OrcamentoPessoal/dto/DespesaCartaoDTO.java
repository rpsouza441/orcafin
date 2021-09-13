package br.com.rodrigo.OrcamentoPessoal.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import br.com.rodrigo.OrcamentoPessoal.domain.DespesaCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespesaCartaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String descricao;
	
	private Date dataLancamento;

	private Date dataVencimento;

	private Date dataPagamento;

	private boolean estaPago;

	private BigDecimal valor;

	private Integer categoriaDespesaID;

	private Integer cartaoID;

	public DespesaCartaoDTO(DespesaCartao obj) {
		this.descricao = obj.getDescricao();
		this.dataLancamento = obj.getDataLancamento();
		this.dataVencimento = obj.getDataVencimento();
		this.dataPagamento = obj.getDataPagamento();
		this.estaPago = obj.isEstaPago();
		this.valor = obj.getValor();
		this.categoriaDespesaID = obj.getCategoriaDespesa().getId();
		this.cartaoID = obj.getCartao().getId();
	}
}
