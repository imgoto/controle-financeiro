package com.fatec.controle_financeiro.controllers;

import com.fatec.controle_financeiro.entities.ContasReceber;
import com.fatec.controle_financeiro.domain.ContasReceber.ContaReceberService;
import com.fatec.controle_financeiro.domain.cliente.ClienteRepository;  // Importe o ClienteRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

import java.util.List;

@RestController
@RequestMapping("/contas-receber")
public class ContaReceberController {

    @Autowired
    private ContaReceberService contaReceberService;

    @Autowired
    private ClienteRepository clienteRepository;  // Use ClienteRepository em vez de ClienteService

    // LISTA TODAS AS CONTAS A RECEBER
    @GetMapping
    public List<ContasReceber> listarTodas() {
        return contaReceberService.listarTodas();
    }

    // BUSCA UMA CONTA A RECEBER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<ContasReceber> buscarPorId(@PathVariable Long id) {
        return contaReceberService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CRIA UMA NOVA CONTA A RECEBER
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody ContasReceber contaReceber) {
        // Verificando se a data de emissão não é posterior à data de vencimento
        if (contaReceber.getEmissao().isAfter(contaReceber.getVencimento())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de emissão não pode ser posterior à data de vencimento.");
        }

        // Verificando se o cliente existe
        if (contaReceber.getCliente() == null || contaReceber.getCliente().getId() == null || 
            !clienteRepository.existsById(contaReceber.getCliente().getId())) {  // Usando o existsById() do ClienteRepository
            return ResponseEntity.badRequest().body("Cliente não encontrado. Verifique se o ID do cliente está correto.");
        }

        // Verificando se o valor é positivo (usando compareTo para BigDecimal)
        if (contaReceber.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("O valor da conta a receber deve ser positivo.");
        }

        ContasReceber created = contaReceberService.criar(contaReceber);
        return ResponseEntity.status(HttpStatus.CREATED).body(created); // Retorna 201 Created
    }

    // ATUALIZA UMA CONTA A RECEBER
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ContasReceber contaReceber) {
        // Verificando se a data de emissão não é posterior à data de vencimento
        if (contaReceber.getEmissao().isAfter(contaReceber.getVencimento())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de emissão não pode ser posterior à data de vencimento.");
        }

        // Verificando se o valor é positivo (usando compareTo para BigDecimal)
        if (contaReceber.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("O valor da conta a receber deve ser positivo.");
        }

        return ResponseEntity.ok(contaReceberService.atualizar(id, contaReceber));
    }

    // EXCLUI UMA CONTA A RECEBER
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        contaReceberService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
