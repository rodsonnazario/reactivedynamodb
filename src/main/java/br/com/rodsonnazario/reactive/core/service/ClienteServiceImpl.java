package br.com.rodsonnazario.reactive.core.service;

import br.com.rodsonnazario.reactive.core.domain.Cliente;
import br.com.rodsonnazario.reactive.core.domain.Endereco;
import br.com.rodsonnazario.reactive.infrastructure.util.Resultado;
import br.com.rodsonnazario.reactive.core.port.in.ClienteService;
import br.com.rodsonnazario.reactive.core.port.out.ClientePort;
import br.com.rodsonnazario.reactive.core.service.exception.ClienteNaoEncontradoException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClientePort clientePort;
    private final LocalDateTime DATA_ATUAL = LocalDateTime.now();

    public ClienteServiceImpl(ClientePort clientePort) {
        this.clientePort = clientePort;
    }

    @Override
    public Mono<Resultado> criaNovoCliente(Cliente cliente) {
        cliente.setDataCriacaoEAtualizacao(DATA_ATUAL);
        return Mono.fromFuture(clientePort.save(cliente))
                .thenReturn(Resultado.SUCCESSO)
                .onErrorReturn(Resultado.FALHA);
    }

    @Override
    public Mono<Cliente> getClienteByClienteId(String clienteId) {
        return Mono.fromFuture(clientePort.findById(clienteId))
                .doOnSuccess(Objects::requireNonNull)
                .onErrorResume(e -> Mono.error(new ClienteNaoEncontradoException("Cliente errado")));
//                .onErrorReturn(new Cliente());
    }

    @Override
    public Mono<Endereco> queryEnderecoByClienteId(String clienteId) {
        return Mono.from(clientePort.findClienteEndereco(clienteId))
                .map(Cliente::getEndereco)
                .doOnSuccess(Objects::requireNonNull)
                .onErrorReturn(new Endereco());
    }

    @Override
    public Mono<Resultado> atualizaClienteExistente(Cliente cliente) {
        cliente.setDataAtualizacao(DATA_ATUAL);
        return Mono.fromFuture(clientePort.findById(cliente.getClienteID()))
                .doOnSuccess(Objects::requireNonNull)
                .doOnNext(__ -> clientePort.update(cliente))
                .doOnSuccess(Objects::requireNonNull)
                .thenReturn(Resultado.SUCCESSO)
                .onErrorReturn(Resultado.FALHA);
    }

    @Override
    public Mono<Resultado> atualizaOuCriaCliente(Cliente cliente) {
        cliente.setDataCriacaoEAtualizacao(DATA_ATUAL);
        return Mono.fromFuture(clientePort.update(cliente))
                .thenReturn(Resultado.SUCCESSO)
                .onErrorReturn(Resultado.FALHA);
    }

    @Override
    public Mono<Resultado> deletaClienteByClienteId(String clienteId) {
        return Mono.fromFuture(clientePort.deleteById(clienteId))
                .doOnSuccess(Objects::requireNonNull)
                .thenReturn(Resultado.SUCCESSO)
                .onErrorReturn(Resultado.FALHA);
    }

    @Override
    public Flux<Cliente> getClienteList() {
        return Flux.from(clientePort.findAll()
                        .items())
                .onErrorReturn(new Cliente());
    }
}