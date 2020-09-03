package com.cartoes.api.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cartoes.api.entities.Cartao;
import com.cartoes.api.entities.Transacao;
import com.cartoes.api.repositories.CartaoRepository;
import com.cartoes.api.repositories.TransacaoRepository;
import com.cartoes.api.utils.ConsistenciaException;

@Service
public class TransacaoService {
	private static final Logger log = LoggerFactory.getLogger(TransacaoService.class);

	@Autowired
	private TransacaoRepository transacaoRepository;

	@Autowired
	private CartaoRepository cartaoRepository;

	public Optional<Transacao> buscarPorId(int id) throws ConsistenciaException {
		log.info("Service: buscando os cartoes de id: {}", id);
		Optional<Transacao> cartao = transacaoRepository.findById(id);
		if (!cartao.isPresent()) {
			log.info("Service: Nenhuma cartão com id: {} foi encontrado", id);
			throw new ConsistenciaException("Nenhuma cartão com id: {} foi encontrado", id);
		}
		return cartao;
	}

	public Optional<List<Transacao>> buscarPorNumero(String numero) throws ConsistenciaException {

		log.info("Service: buscando transações do cartão de numero: {}", numero);

		Optional<List<Transacao>> transacao = Optional.ofNullable(transacaoRepository.findByNum(numero));

		if (!transacao.isPresent()) {
			log.info("Service: Nenhuma transacao do cartão: {} foi encontrada", numero);
			throw new ConsistenciaException("Nenhuma transacao do cartão: {} foi encontrada", numero);
		}

		return transacao;

	}

	public Transacao salvar(Transacao transacao) throws ConsistenciaException {

		log.info("Service: salvando a transação: {}", transacao);

		
		if (!Optional.ofNullable(cartaoRepository.findByNumero(transacao.getCartoes().getNumero())).isPresent()) {
			log.info("Service: Nenhuma transação com cartão de numero: {} foi encontrada",
			transacao.getCartoes().getNumero());
			throw new ConsistenciaException("Service: Nenhuma transação com cartão de numero: {} foi encontrada",
			transacao.getCartoes().getNumero());
		}
		
		Cartao cartao = cartaoRepository.findByNumero(transacao.getCartoes().getNumero());
		transacao.setCartoes(cartao);

		if (transacao.getCartoes().getBloqueado()) {
			log.info(
					"Service: Não é possível incluir transações para este cartão, pois o mesmo encontra-se bloqueado. ");
			throw new ConsistenciaException(
					"Service: Não é possível incluir transações para este cartão, pois o mesmo encontra-se bloqueado. ");
		}

		if (transacao.getCartoes().getDataValidade().before(new Date())) {
			log.info("Service: Não é possível incluir transações para este cartão, pois o mesmo encontra-se vencido");
			throw new ConsistenciaException(
					"Service: Não é possível incluir transações para este cartão, pois o mesmo encontra-se vencido");
		}

		if (transacao.getId() > 0) {
			log.info("Service: Transações não podem ser alteradas, apenas incluídas");
			throw new ConsistenciaException("Service: Transações não podem ser alteradas, apenas incluídas");
		}

		try {
			return transacaoRepository.save(transacao);
		} catch (DataIntegrityViolationException e) {
			log.info("Service: Erro ao inserir transação");
			throw new ConsistenciaException("Erro ao inserir transação");
		}

	}
}