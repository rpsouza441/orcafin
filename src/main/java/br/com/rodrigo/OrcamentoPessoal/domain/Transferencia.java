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
public class Transferencia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;

	private Date data;

	private BigDecimal valor;

	@JsonIgnore
    @Getter(onMethod = @__( @JsonIgnore ))
	@ManyToOne
	@JoinColumn(name="conta_origem_id")
	private Conta contaOrigem;
	
	@JsonIgnore
    @Getter(onMethod = @__( @JsonIgnore ))
	@ManyToOne
	@JoinColumn(name="conta_destino_id")
	private Conta contaDestino;
	
}
