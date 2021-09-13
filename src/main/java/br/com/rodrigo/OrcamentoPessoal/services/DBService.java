package br.com.rodrigo.OrcamentoPessoal.services;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodrigo.OrcamentoPessoal.domain.Cartao;
import br.com.rodrigo.OrcamentoPessoal.domain.CategoriaDespesa;
import br.com.rodrigo.OrcamentoPessoal.domain.CategoriaReceita;
import br.com.rodrigo.OrcamentoPessoal.domain.Conta;
import br.com.rodrigo.OrcamentoPessoal.domain.Despesa;
import br.com.rodrigo.OrcamentoPessoal.domain.DespesaCartao;
import br.com.rodrigo.OrcamentoPessoal.domain.Receita;
import br.com.rodrigo.OrcamentoPessoal.domain.Transferencia;
import br.com.rodrigo.OrcamentoPessoal.domain.Usuario;
import br.com.rodrigo.OrcamentoPessoal.domain.enums.Perfil;
import br.com.rodrigo.OrcamentoPessoal.domain.enums.TipoBandeira;
import br.com.rodrigo.OrcamentoPessoal.domain.enums.TipoConta;
import br.com.rodrigo.OrcamentoPessoal.repositories.CartaoRepository;
import br.com.rodrigo.OrcamentoPessoal.repositories.CategoriaDespesaRepository;
import br.com.rodrigo.OrcamentoPessoal.repositories.CategoriaReceitaRepository;
import br.com.rodrigo.OrcamentoPessoal.repositories.ContaRepository;
import br.com.rodrigo.OrcamentoPessoal.repositories.DespesaCartaoRepository;
import br.com.rodrigo.OrcamentoPessoal.repositories.DespesaRepository;
import br.com.rodrigo.OrcamentoPessoal.repositories.ReceitaRepository;
import br.com.rodrigo.OrcamentoPessoal.repositories.UsuarioRepository;

@Service
public class DBService {
	@Autowired
	private CategoriaDespesaRepository categoriaDespesaRepo;
	
	@Autowired
	private CategoriaReceitaRepository categoriaReceitaRepo;

	@Autowired
	private UsuarioRepository usuarioRepo;

	@Autowired
	private ContaRepository contaRepo;

	@Autowired
	private CartaoRepository cartaoRepo;
	
	
	@Autowired
	private ReceitaRepository receitaRepo;
	
	@Autowired
	private DespesaCartaoRepository despesaCCRepo;
	
	@Autowired
	private DespesaRepository despesaRepo;
	
	@Autowired
	private TransferenciaService transfService;

	public void instantiateTestDatabase() throws ParseException {
		CategoriaDespesa cateDespesa1 = CategoriaDespesa.builder().id(null).nome("Moradia").build();
		CategoriaDespesa cateDespesa2 = CategoriaDespesa.builder().id(null).nome("Aliemntação").build();
		CategoriaDespesa cateDespesa3 = CategoriaDespesa.builder().id(null).nome("Estudos").build();
		CategoriaDespesa cateDespesa4 = CategoriaDespesa.builder().id(null).nome("Roupa").build();
		
		CategoriaReceita cateReceita1 = CategoriaReceita.builder().id(null).nome("Salario").build();
		CategoriaReceita cateReceita2 = CategoriaReceita.builder().id(null).nome("Premio").build();
		CategoriaReceita cateReceita3 = CategoriaReceita.builder().id(null).nome("Outros").build();

		Usuario user1 = Usuario.builder().id(null).nome("Rodrigo").email("rpsouzati@gmail.com").senha("123").build();
		user1.addPerfil(Perfil.ADMIN);

		Conta conta1 = Conta.builder().id(null).descricao("").instituicaoFinanceira("itau").user(user1)
				.tipo(TipoConta.CONTA_CORRENTE).build();
		Conta conta2 = Conta.builder().id(null).descricao("").instituicaoFinanceira("itau").user(user1)
				.tipo(TipoConta.CONTA_POUPANCA).build();
		user1.addConta(conta1);
		user1.addConta(conta2);
		
		Cartao cartao1 = Cartao.builder().id(null).descricao("Cartao Principal").bandeira(TipoBandeira.MASTERCARD)
				.limite(new BigDecimal(1500)).diaFechamento(01).diaVencimento(15)
				.contaParaDebitar(conta1).user(user1).build();
		user1.AddCartao(cartao1);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Receita r1= Receita.builder().id(null).descricao("Pagamento")
				.dataLancamento(sdf.parse("02/09/2021 10:32"))
				.dataVencimento(sdf.parse("05/09/2021 00:00"))
				.dataRecebimento(sdf.parse("04/09/2021 00:00"))
				.foiRecebido(true)
				.valor(new BigDecimal(1200))
				.categoriaReceita(cateReceita1)
				.conta(conta1)
				.build();
		conta1.addReceita(r1);
		
		
		Despesa d1= Despesa.builder().id(null).descricao("conta de luz")
				.dataLancamento(sdf.parse("01/09/2021 10:32"))
				.dataVencimento(sdf.parse("15/09/2021 01:00"))
				.dataPagamento(sdf.parse("10/09/2021 00:20"))
				.estaPago(true)
				.valor(new BigDecimal(150))
				.categoriaDespesa(cateDespesa1)
				.conta(conta1)
				.build();
		
		conta1.addDespesa(d1);

		
		DespesaCartao dCC1= DespesaCartao.builder().id(null).descricao("Roupa")
				.dataLancamento(sdf.parse("20/08/2021 10:32"))
				.dataVencimento(sdf.parse("15/09/2021 10:00"))
				.estaPago(false)
				.valor(new BigDecimal(30))
				.categoriaDespesa(cateDespesa4)
				.cartao(cartao1)
				.build();
		cartao1.addDespesa(dCC1);
		
		
		Transferencia t1 = Transferencia.builder().id(null).data(sdf.parse("08/09/2021 18:32")).valor(new BigDecimal(500))
				.contaOrigem(conta1)
				.contaDestino(conta2)
				.build();
		
		
		categoriaDespesaRepo.saveAll(Arrays.asList(cateDespesa1, cateDespesa2, cateDespesa3, cateDespesa4));
		categoriaReceitaRepo.saveAll(Arrays.asList(cateReceita1, cateReceita2, cateReceita3));
		
		usuarioRepo.saveAll(Arrays.asList(user1));
		contaRepo.saveAll(Arrays.asList(conta1, conta2));
		cartaoRepo.saveAll(Arrays.asList(cartao1));
		
		receitaRepo.saveAll(Arrays.asList(r1));
		despesaRepo.saveAll(Arrays.asList(d1));
		despesaCCRepo.saveAll(Arrays.asList(dCC1));
		transfService.insert(t1);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
