package br.com.rodrigo.OrcamentoPessoal.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.rodrigo.OrcamentoPessoal.domain.enums.Perfil;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	private String nome;

	@Column(unique = true)
	private String email;
	@JsonIgnore
	private String senha;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Conta> listaContas = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Cartao> listaCartoes = new ArrayList<>();

	@Builder.Default
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PERFIS")
	private Set<Integer> perfis = new HashSet<>();

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		if (perfis == null) {
			perfis = new HashSet<>();

		}
		perfis.add(perfil.getCod());
	}

	public Usuario() {
		addPerfil(Perfil.CLIENTE);

	}

	public Usuario(Integer id, String nome, String email, String senha) {
		super();
		addPerfil(Perfil.CLIENTE);
		this.id = id;
		this.nome = nome;
		this.senha = senha;
		this.email = email;
	}

	public Usuario(Integer id, String nome, String email, String senha, List<Conta> listaContas,
			List<Cartao> listaCartoes, Set<Integer> perfis) {
		super();
		this.id = id;
		addPerfil(Perfil.CLIENTE);
		this.listaContas = listaContas;
		this.listaCartoes = listaCartoes;
		this.nome = nome;
		this.senha = senha;
		this.email = email;
	}

	public void addConta(Conta conta) {
		if (listaContas == null) {
			listaContas = new ArrayList<>();
		}
		listaContas.add(conta);

	}

	public void AddCartao(Cartao cartao) {
		if (listaCartoes == null) {
			listaCartoes = new ArrayList<>();
		}
		listaCartoes.add(cartao);
	}

}
