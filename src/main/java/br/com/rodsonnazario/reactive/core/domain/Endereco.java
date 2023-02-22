package br.com.rodsonnazario.reactive.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
@Getter
@Setter
@ToString
public class Endereco {

    private String cidade;
    private String estado;
    private String pais;
}