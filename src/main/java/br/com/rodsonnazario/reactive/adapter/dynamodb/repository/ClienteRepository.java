package br.com.rodsonnazario.reactive.adapter.dynamodb.repository;

import br.com.rodsonnazario.reactive.core.domain.Cliente;
import br.com.rodsonnazario.reactive.core.port.out.ClientePort;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.async.SdkPublisher;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;

import java.util.concurrent.CompletableFuture;

import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.keyEqualTo;

@Repository
public class ClienteRepository implements ClientePort {

    private final DynamoDbAsyncTable<Cliente> clienteAsyncTable;

    public ClienteRepository(DynamoDbAsyncTable<Cliente> clienteAsyncTable) {
        this.clienteAsyncTable = clienteAsyncTable;
    }

    //CREATE
    @Override
    public CompletableFuture<Void> save(Cliente cliente) {
        return clienteAsyncTable.putItem(cliente);
    }

    //READ
    @Override
    public CompletableFuture<Cliente> findById(String clienteId) {
        return clienteAsyncTable.getItem(getKeyBuild(clienteId));
    }

    //UPDATE
    @Override
    public CompletableFuture<Cliente> update(Cliente cliente) {
        return clienteAsyncTable.updateItem(cliente);
    }

    //DELETE
    @Override
    public CompletableFuture<Cliente> deleteById(String clienteId) {
        return clienteAsyncTable.deleteItem(getKeyBuild(clienteId));
    }

    //READ_CLIENTE_ENDERECO_APENAS
    @Override
    public SdkPublisher<Cliente> findClienteEndereco(String clienteId) {
        return clienteAsyncTable.query(r -> r.queryConditional(keyEqualTo(k -> k.partitionValue(clienteId))).addAttributeToProject("ClienteEndereco")).items();
    }

    //GET_ALL_ITEM
    @Override
    public PagePublisher<Cliente> findAll() {
        return clienteAsyncTable.scan();
    }

    private Key getKeyBuild(String clienteId) {
        return Key.builder().partitionValue(clienteId).build();
    }
}