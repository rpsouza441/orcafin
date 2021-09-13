package br.com.rodrigo.OrcamentoPessoal.domain.enums;

import lombok.Getter;

@Getter
public enum TipoBandeira {
	
	MASTERCARD(1, "MasterCard"), 
	VISA(2, "Visa"), 
	HIPERCARD(3, "HiperCard"), 
	AMERICAN_EXPRESS(4, "American Express"), 
	SOROCARD(5, "SoroCard"), 
	BNDS(6, "BNDS"), 
	DINNERS(7, "Dinners"), 
	ELO(8, "Elo"), 
	OUTROS(9, "Outros"); 

	private int cod;
	private String descricao;

	private TipoBandeira(int cod, String descricao) {
		this.cod = cod; 
		this.descricao = descricao;
	}

	public static TipoBandeira toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (TipoBandeira x : TipoBandeira.values()) {
			if (cod.equals(x.getCod())) {
				return x;

			}
		}
		throw new IllegalArgumentException("Id Inválido" + cod);
	}
}
