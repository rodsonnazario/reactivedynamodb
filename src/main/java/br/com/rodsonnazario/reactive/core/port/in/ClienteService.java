package br.com.rodsonnazario.reactive.core.port.in;

import br.com.rodsonnazario.reactive.core.domain.Cliente;
import br.com.rodsonnazario.reactive.core.domain.Endereco;
import br.com.rodsonnazario.reactive.infrastructure.util.Resultado;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClienteService {

    Mono<Resultado> criaNovoCliente(Cliente cliente);
    Mono<Cliente> getClienteByClienteId(String clienteId);
    Mono<Endereco> queryEnderecoByClienteId(String clienteId);
    Mono<Resultado> atualizaClienteExistente(Cliente cliente);
    Mono<Resultado> atualizaOuCriaCliente(Cliente cliente);
    Mono<Resultado> deletaClienteByClienteId(String clienteId);
    Flux<Cliente> getClienteList();
}
