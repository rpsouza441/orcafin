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

import br.com.rodrigo.OrcamentoPessoal.domain.enums.TipoConta;
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
public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	private BigDecimal saldo;
	private String descricao;
	private String instituicaoFinanceira;
	private Integer tipo;

	@JsonIgnore
	@Getter(onMethod = @__(@JsonIgnore))
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Usuario user;

	@OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
	private List<Receita> listaReceitas;

	@OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
	private List<Despesa> listaDespesas;

	@OneToMany(mappedBy = "contaParaDebitar", cascade = CascadeType.ALL)
	private List<Cartao> listaCartoes;

	@OneToMany(mappedBy = "contaDestino", cascade = CascadeType.ALL)
	private List<Transferencia> listaTransfRecebidas;

	@OneToMany(mappedBy = "contaOrigem", cascade = CascadeType.ALL)
	private List<Transferencia> listaTransfRealizadas;

	public static class ContaBuilder {
		public ContaBuilder tipo(TipoConta tipo) {
			this.tipo = tipo.getCod();
			return this;
		}
	}

	public TipoConta getTipo() {
		return TipoConta.toEnum(tipo);
	}

	public void setTipo(TipoConta tipo) {
		this.tipo = tipo.getCod();
	}

	public void addReceita(Receita receita) {
		if (listaReceitas == null) {
			listaReceitas = new ArrayList<>();
		}
		if (saldo == null) {
			saldo = new BigDecimal(0);
		}
		saldo = saldo.add(receita.getValor());
		listaReceitas.add(receita);

	}

	public void addDespesa(Despesa despesa) {
		if (listaDespesas == null) {
			listaDespesas = new ArrayList<>();
		}
		if (saldo == null) {
			saldo = new BigDecimal(0);
		}
		saldo = saldo.subtract(despesa.getValor());
		listaDespesas.add(despesa);

	}

	public void retirarSaldo(BigDecimal valor) {
		if (saldo == null) {
			saldo = new BigDecimal(0);
		}
		saldo = saldo.subtract(valor);
	}

	public void adicionarSaldo(BigDecimal valor) {
		if (saldo == null) {
			saldo = new BigDecimal(0);
		}
		saldo = saldo.add(valor);

	}

}
