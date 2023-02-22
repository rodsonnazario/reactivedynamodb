package br.com.rodsonnazario.reactive.core.domain;

import lombok.Setter;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.LocalDateTime;

@DynamoDbBean
@Setter
@ToString
public class Cliente {
    private String clienteID;
    private String nome;
    private String telefone;
    private Endereco endereco;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("ClienteID")
    public String getClienteID() {
        return clienteID;
    }

    @DynamoDbAttribute("ClienteNome")
    public String getNome() {
        return nome;
    }

    @DynamoDbAttribute("ClienteTelefone")
    public String getTelefone() {
        return telefone;
    }

    @DynamoDbAttribute("ClienteEndereco")
    public Endereco getEndereco() {
        return endereco;
    }

    @DynamoDbAttribute("ClienteDataCriacao")
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    @DynamoDbAttribute("ClienteDataAtualizacao")
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataCriacaoEAtualizacao(LocalDateTime dataCriacaoEAtualizacao) {
        this.dataCriacao = dataCriacaoEAtualizacao;
        this.dataAtualizacao = dataCriacaoEAtualizacao;
    }
}
