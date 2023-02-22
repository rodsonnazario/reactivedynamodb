package br.com.rodsonnazario.reactive.adapter.web;

import br.com.rodsonnazario.reactive.core.domain.Cliente;
import br.com.rodsonnazario.reactive.core.domain.Endereco;
import br.com.rodsonnazario.reactive.infrastructure.util.Resultado;
import br.com.rodsonnazario.reactive.core.port.in.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Resultado> salva(@RequestBody Cliente cliente) {
        return clienteService.criaNovoCliente(cliente);
    }

    @GetMapping("/{clienteId}")
    public Mono<Cliente> getCliente(@PathVariable("clienteId") String clienteId) {
        return clienteService.getClienteByClienteId(clienteId);
    }

    @PutMapping()
    public Mono<Resultado> atualizaOuCriaCliente(@RequestBody Cliente cliente) {
        return clienteService.atualizaOuCriaCliente(cliente);
    }

    @DeleteMapping("/{clienteId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Resultado> deleta(@PathVariable("clienteId") String clienteId) {
        return clienteService.deletaClienteByClienteId(clienteId);
    }

    @PutMapping("/cadastrado")
    public Mono<Resultado> atualizaCliente(@RequestBody Cliente cliente) {
        return clienteService.atualizaClienteExistente(cliente);
    }

    @GetMapping("/endereco/{clienteId}")
    public Mono<Endereco> getClienteEndereco(@PathVariable("clienteId") String clienteId) {
        return clienteService.queryEnderecoByClienteId(clienteId);
    }

    @GetMapping()
    public Flux<Cliente> getAllCliente() {
        return clienteService.getClienteList();
    }
}
