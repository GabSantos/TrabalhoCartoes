package com.cartoes.api.services;

import java.util.Optional;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cartoes.api.entities.Transacao;
import com.cartoes.api.repositories.TransacaoRepository;
import com.cartoes.api.utils.ConsistenciaException;

@Service
public class TransacaoService {
	private static final Logger log = LoggerFactory.getLogger(TransacaoService.class);

	@Autowired
	private TransacaoRepository transacaoRepository;

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

		Optional<List<Transacao>> transacao = Optional.ofNullable(transacaoRepository.findByNumero(numero));

		if (!transacao.isPresent()) {
			log.info("Service: Nenhuma transacao do cartão: {} foi encontrada", numero);
			throw new ConsistenciaException("Nenhuma transacao do cartão: {} foi encontrada", numero);
		}

		return transacao;

	}

	public Transacao salvar(Transacao transacao) throws ConsistenciaException {

		log.info("Service: salvando a transação: {}", transacao);

		if (transacao.getId() > 0)
			buscarPorId(transacao.getId());

		
		return transacaoRepository.save(transacao);
		
	}
}