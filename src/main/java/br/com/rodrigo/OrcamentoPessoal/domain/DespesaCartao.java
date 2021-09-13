package br.com.rodrigo.OrcamentoPessoal.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class DespesaCartao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;

	private String descricao;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date dataLancamento;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date dataVencimento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date dataPagamento;

	private boolean estaPago;
	
	private BigDecimal valor;
	
	@JsonIgnore
    @Getter(onMethod = @__( @JsonIgnore ))
	@ManyToOne
	@JoinColumn(name="categoria_despesa_id")
	private CategoriaDespesa categoriaDespesa;
	
	@JsonIgnore
    @Getter(onMethod = @__( @JsonIgnore ))
	@ManyToOne
	@JoinColumn(name="contao_id")
	private Cartao cartao;

	
}
