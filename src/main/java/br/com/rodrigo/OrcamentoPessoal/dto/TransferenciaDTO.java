package br.com.rodrigo.OrcamentoPessoal.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import br.com.rodrigo.OrcamentoPessoal.domain.Transferencia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Date data;

	private BigDecimal valor;

	private Integer contaOrigemID;

	private Integer contaDestinoID;

	public TransferenciaDTO(Transferencia obj) {
		this.data = obj.getData();
		this.valor = obj.getValor();
		this.contaDestinoID = obj.getContaDestino().getId();
		this.contaOrigemID = obj.getContaOrigem().getId();
	}
}
