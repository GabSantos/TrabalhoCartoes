package com.cartoes.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import com.cartoes.api.entities.Transacao;

@Transactional(readOnly = true)
public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {
    @Query("SELECT num  FROM Transacao num WHERE num.cartao.numero = :numero")
    Optional<List<Transacao>> findByNum(@Param("numero") String numero);
    
}
