package com.fatec.controle_financeiro.controllers;

import com.fatec.controle_financeiro.entities.Cliente;
import com.fatec.controle_financeiro.entities.ContasReceber;
import com.fatec.controle_financeiro.domain.cliente.ClienteRepository;
import com.fatec.controle_financeiro.domain.ContasReceber.ContasReceberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContasReceberRepository contasReceberRepository;

    // CREATE Cliente
    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        if (cliente.getName() == null || cliente.getName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Cliente createdCliente = clienteRepository.save(cliente);
            return new ResponseEntity<>(createdCliente, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // LISTAR TODOS OS CLIENTES
    @GetMapping
    public ResponseEntity<List<Cliente>> getAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    // OBTER CLIENTE POR ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            return new ResponseEntity<>(clienteOptional.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }
    }

// ATUALIZAR CLIENTE
@PutMapping("/{id}")
public ResponseEntity<?> atualizar(@PathVariable long id, @RequestBody Cliente clienteAtualizado) {
    Optional<Cliente> clienteOptional = clienteRepository.findById(id);
    
    if (clienteOptional.isPresent()) {
        Cliente cliente = clienteOptional.get();
        cliente.setName(clienteAtualizado.getName());
        // Atualize outros campos conforme necessário
        // Exemplo: cliente.setEmail(clienteAtualizado.getEmail());
        
        Cliente updatedCliente = clienteRepository.save(cliente);
        return ResponseEntity.ok(updatedCliente); // Retorna 200 OK com o cliente atualizado
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado"); // Retorna 404 se não encontrar
    }
}


    // DELETAR CLIENTE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }
    }

    // CREATE Contas a Receber para um Cliente
    @PostMapping("/{id}/contas-receber")
    public ResponseEntity<ContasReceber> createContaReceber(@PathVariable long id, @RequestBody ContasReceber contaReceber) {
        try {
            Optional<Cliente> clienteOptional = clienteRepository.findById(id);
            if (clienteOptional.isPresent()) {
                contaReceber.setCliente(clienteOptional.get());
                ContasReceber createdContaReceber = contasReceberRepository.save(contaReceber);
                return new ResponseEntity<>(createdContaReceber, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE Contas a Receber para um Cliente
    @DeleteMapping("/{clienteId}/contas-receber/{contaId}")
    public ResponseEntity<?> deleteContaReceber(@PathVariable long clienteId, @PathVariable long contaId) {
        // Verifica se a conta a receber existe
        Optional<ContasReceber> contaOptional = contasReceberRepository.findById(contaId);
        if (contaOptional.isPresent()) {
            ContasReceber conta = contaOptional.get();
            // Verifica se a conta pertence ao cliente
            if (conta.getCliente().getId() == clienteId) {
                contasReceberRepository.deleteById(contaId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta a receber não pertence ao cliente");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta a receber não encontrada");
        }
    }
}
