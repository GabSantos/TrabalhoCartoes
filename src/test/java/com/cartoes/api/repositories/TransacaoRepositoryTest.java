package com.cartoes.api.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cartoes.api.entities.Cartao;
import com.cartoes.api.entities.Cliente;
import com.cartoes.api.entities.Transacao;



@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TransacaoRepositoryTest {

	@Autowired
	private TransacaoRepository transacaoRepository;

	@Autowired
	private CartaoRepository cartaoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	private Transacao transacaoTeste;

	private Cartao cartaoTeste;

	private Cliente clienteTeste;

	private void CriarTransacaoTestes() throws ParseException {
		
		transacaoTeste = new Transacao();
		
		transacaoTeste.setCnpj("12312312312312");
		transacaoTeste.setJuros(0.5);
		transacaoTeste.setCartoes(cartaoTeste);
		transacaoTeste.setQtdParcelas(12);
		transacaoTeste.setValor(1000.00);
		transacaoTeste.prePersist();
	}

	private void CriarClienteTestes() throws ParseException {
		
		clienteTeste = new Cliente();
		
		clienteTeste.setNome("Nome Teste");
		clienteTeste.setCpf("05887098082");
		clienteTeste.setUf("CE");
		clienteTeste.setDataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"));
		
	}

	private void CriarCartaoTestes() throws ParseException {
		
		cartaoTeste = new Cartao();
		
		cartaoTeste.setNumero("1111111111111111");
		cartaoTeste.setDataValidade(new SimpleDateFormat("dd/MM/yyyy").parse("28/12/2024"));
		cartaoTeste.setBloqueado(false);
		cartaoTeste.setDataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("12/9/2020"));
		cartaoTeste.setCliente(clienteTeste);
		
	}
	
	@Before
	public void setUp() throws Exception {
		
		CriarClienteTestes();
		CriarCartaoTestes();
		CriarTransacaoTestes();
		clienteRepository.save(clienteTeste);
		cartaoRepository.save(cartaoTeste);
		transacaoRepository.save(transacaoTeste);
		
	}
	
	@After
	public void tearDown() throws Exception {
		
		transacaoRepository.deleteAll();
		cartaoRepository.deleteAll();
		clienteRepository.deleteAll();
		
	}
	
	@Test
	public void testFindById() {	
		
		Transacao transacao = transacaoRepository.findById(transacaoTeste.getId()).get();
		assertEquals(transacaoTeste.getId(), transacao.getId());
		
	}
	
	@Test
	public void testFindByClienteId() {
		
		Optional<List<Transacao>> lstTransacao = transacaoRepository.findByNum(transacaoTeste.getCartoes().getNumero());
		if(lstTransacao.get().size() != 1) {
			fail();
		}
		
		Transacao transacao = lstTransacao.get().get(0);
		
		assertEquals(transacaoTeste.getCartoes().getNumero(), transacao.getCartoes().getNumero());		}

}
