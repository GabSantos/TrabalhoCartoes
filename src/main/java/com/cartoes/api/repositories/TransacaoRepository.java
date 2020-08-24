package com.cartoes.api.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.cartoes.api.entities.Transacao;

@Transactional(readOnly = true)
public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {
    @Query("SELECT numero FROM Transacao numero WHERE numero.cartao.numero = :numero")
    List<Transacao> findByNumero(@Param("numero") String numero);
}
