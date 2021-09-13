package br.com.rodrigo.OrcamentoPessoal.domain.enums;

import lombok.Getter;

@Getter
public enum TipoConta {

	CONTA_CORRENTE(1, "Conta Corrente"),
	CONTA_POUPANCA(2, "Conta Poupança"),
	DINHEIRO(3, "Dinheiro"),
	INVESTIMENTO(4, "Investimento"),
	CONTA_SALARIO(5, "Conta Salário"),
	OUTROS(6, "Outros"); 
	
	
	private int cod;
	private String descricao;

	private TipoConta(int cod, String descricao) {
		this.cod = cod; 
		this.descricao = descricao;
	}

	public static TipoConta toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (TipoConta x : TipoConta.values()) {
			if (cod.equals(x.getCod())) {
				return x;

			}
		}
		throw new IllegalArgumentException("Id Inválido" + cod);
	}
}
