package com.cartoes.api.dtos;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

public class TransacaoDto {

    private String id;

    private String dataTransacao;

    @NotEmpty(message = "O cnpj não pode ser vazio")
    @CNPJ(message = "O cnpj deve ser válido")
    private String cnpj;

    @NotEmpty(message = "O valor não pode ser vazi0")
    @Length(max = 10, message = "Valor pode ter até 10 caracteres numéricos")
    private String valor;

    @NotEmpty(message = "A quantidade de parcelas não pode ser vazia")
    @Length(max = 2, message = "Juros pode ter até 2 caracteres numéricos")
    private String qtdParcelas;

    @NotEmpty(message = "O juros não pode ser vazio")
    @Length(max = 4, message = "Juros pode ter até 4 caracteres")
    private String juros;

    @NotEmpty(message = "O ID do cartão não pode ser vazio.")
    private String cartaoId;

    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(String dataTransacao){
        this.dataTransacao = dataTransacao;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj){
        this.cnpj = cnpj;
    }
    public String getValor() {
        return valor;
    }

    public void setValor(String valor){
        this.valor = valor;
    }
    public String getQtdParcelas() {
        return qtdParcelas;
    }

    public void setQtdParcelas(String qtdParcelas){
        this.qtdParcelas = qtdParcelas;
    }
    public String getJuros() {
        return juros;
    }

    public void setJuros(String juros){
        this.juros = juros;
    }
    public String getCartaoId() {
        return cartaoId;
    }

    public void setCartaoId(String cartaoId){
        this.cartaoId = cartaoId;
    }

    @Override
    public String toString() {
        return "Transacao[id=" + id + "," + 
            "dataTransacao=" + dataTransacao + "," +
            "cnpj" + cnpj + "," +
            "valor" + valor + "," +
            "qtdParcelas" + qtdParcelas + "," +
            "juros" + juros + "," +
            "cartaoId" + cartaoId + "]";
    }
}