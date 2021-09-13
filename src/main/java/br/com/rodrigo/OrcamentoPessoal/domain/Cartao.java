package br.com.rodrigo.OrcamentoPessoal.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.rodrigo.OrcamentoPessoal.domain.enums.TipoBandeira;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cartao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;

	private String descricao;
	private Integer bandeira;
	private BigDecimal limite;
	private Integer diaFechamento;
	private Integer diaVencimento;

	@JsonIgnore
	@Getter(onMethod = @__(@JsonIgnore))
	@ManyToOne
	@JoinColumn(name = "conta_debitar_id")
	private Conta contaParaDebitar;

	@JsonIgnore
	@Getter(onMethod = @__(@JsonIgnore))
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Usuario user;

	@OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
	private List<DespesaCartao> listaDespesasCartao;

	public static class CartaoBuilder {
		public CartaoBuilder bandeira(TipoBandeira bandeira) {
			this.bandeira = bandeira.getCod();
			return this;
		}
	}

	public TipoBandeira getBandeira() {
		return TipoBandeira.toEnum(bandeira);
	}

	public void setBandeira(TipoBandeira bandeira) {
		this.bandeira = bandeira.getCod();
	}

	public void addDespesa(DespesaCartao despesaCartao) {
		if (listaDespesasCartao == null) {
			listaDespesasCartao = new ArrayList<>();
		}
		if (limite == null) {
			limite = new BigDecimal(0);
		}
		limite = limite.subtract(despesaCartao.getValor());
		listaDespesasCartao.add(despesaCartao);

	}

}
