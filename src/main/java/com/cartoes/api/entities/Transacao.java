package com.cartoes.api.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "transacao")
public class Transacao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "data_Transacao", nullable = false, length = 100)
    private Date dataTransacao;

    @Column(name = "cnpj", nullable = false, length = 14)
    private String cnpj;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @Column(name = "qdt_Parcelas", nullable = false)
    private int qdtParcelas;

    @Column(name = "juros", nullable = false)
    private Double juros;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Cartao cartao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(Date dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Double getValor() {
        return valor;
    }

    public void setUf(Double valor) {
        this.valor = valor;
    }

    public int getQdtParcelas() {
        return qdtParcelas;
    }

    public void setJuros(Double juros) {
        this.juros = juros;
    }

    public Cartao getCartoes() {
        return cartao;
    }

    public void setCartoes(Cartao cartao) {
        this.cartao = cartao;
    }

    @PrePersist
    public void prePersist() {
        dataTransacao = new Date();
    }

    @Override
    public String toString() {
        return "Transacao[" + "id=" + id + "," + "dataTransacao=" + dataTransacao + "," + "cnpj=" + cnpj + "," + "valor=" + valor + ","
                + "qdtParcelas=" + qdtParcelas + "," + "juros=" + juros + "]";
    }

}
