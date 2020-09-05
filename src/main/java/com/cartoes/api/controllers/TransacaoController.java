package com.cartoes.api.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartoes.api.entities.Transacao;
import com.cartoes.api.response.Response;
import com.cartoes.api.services.TransacaoService;
import com.cartoes.api.utils.ConsistenciaException;


@RestController
@RequestMapping("/api/transacao")
@CrossOrigin(origins = "*")
public class TransacaoController {

	private static final Logger log = LoggerFactory.getLogger(TransacaoController.class);
	
	@Autowired
	private TransacaoService transacaoService;
	
	@GetMapping(value = "cartao/{numero}")
	public ResponseEntity<Response<List<Transacao>>> buscarPorNumero(@PathVariable("numero") String numero){
		
		Response<List<Transacao>> response = new Response<List<Transacao>>();
		
		try {
			log.info("Controller: buscando transações do cartão de Numero: {}", numero);
			
			Optional<List<Transacao>> listaTransacoes = transacaoService.buscarPorNumero(numero);
			
			response.setDados(listaTransacoes.get());
			return ResponseEntity.ok(response);
		} catch (ConsistenciaException e) {
			log.info("Controller: Inconsistencia de dados {}", e.getMensagem());
			response.adicionarErro(e.getMensagem());
			return ResponseEntity.badRequest().body(response);			
		} catch (Exception e) {
			log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
			response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
			return ResponseEntity.status(500).body(response);
		}
	}

	@PostMapping
	public ResponseEntity<Response<Transacao>> salvar(@RequestBody Transacao transacao){
		
		Response<Transacao> response = new Response<Transacao>();
		
		try {

			log.info("Controller: salvando a transacao: {}", transacao.toString());
			response.setDados(this.transacaoService.salvar(transacao));
			return ResponseEntity.ok(response);
		} catch (ConsistenciaException e) {
			log.info("Controller: Inconsistência de dados: {}", e.getMensagem());
			response.adicionarErro(e.getMensagem());
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
			response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
			return ResponseEntity.status(500).body(response);
		}
	}
}