package br.com.rodsonnazario.reactive.core.port.out;

import br.com.rodsonnazario.reactive.core.domain.Cliente;
import software.amazon.awssdk.core.async.SdkPublisher;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;

import java.util.concurrent.CompletableFuture;

public interface ClientePort {

    CompletableFuture<Void> save(Cliente cliente);
    CompletableFuture<Cliente> findById(String clienteId);
    CompletableFuture<Cliente> update(Cliente cliente);
    CompletableFuture<Cliente> deleteById(String clienteId);
    SdkPublisher<Cliente> findClienteEndereco(String clienteId);
    PagePublisher<Cliente> findAll();
}
