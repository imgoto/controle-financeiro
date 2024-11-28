package com.fatec.controle_financeiro.controllers;

import com.fatec.controle_financeiro.entities.ContasPagar;
import com.fatec.controle_financeiro.domain.contaspagar.ContaPagarService;
import com.fatec.controle_financeiro.domain.fornecedor.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal; // Importando BigDecimal
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contas-pagar")
public class ContaPagarController {

    @Autowired
    private ContaPagarService contaPagarService;

    @Autowired
    private FornecedorService fornecedorService;

    // LISTA TODAS AS CONTAS A PAGAR
    @GetMapping
    public ResponseEntity<List<ContasPagar>> findAll() {
        List<ContasPagar> contasPagar = contaPagarService.findAll();
        return ResponseEntity.ok(contasPagar);
    }

    // BUSCA POR ID
    @GetMapping("/{id}")
    public ResponseEntity<ContasPagar> findById(@PathVariable Long id) {
        Optional<ContasPagar> contasPagar = contaPagarService.findById(id);
        return contasPagar.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
    }

    // CRIA UMA NOVA CONTA A PAGAR
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ContasPagar contasPagar) {
        try {
            // Verificando se o fornecedor existe
            if (contasPagar.getFornecedor() == null || 
                !fornecedorService.existsById(contasPagar.getFornecedor().getId())) {
                return ResponseEntity.badRequest().body("Fornecedor não encontrado. Verifique se o ID do fornecedor está correto.");
            }

            // Verificando se o valor é positivo
            if (contasPagar.getValor().compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("O valor da conta a pagar deve ser positivo.");
            }

            // Tentando salvar a conta a pagar
            ContasPagar created = contaPagarService.save(contasPagar);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);

        } catch (ResponseStatusException e) {
            // Captura o erro de validação de data e retorna uma resposta personalizada
            if (e.getMessage().contains("A data de emissão não pode ser posterior")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Não foi possível criar a conta a pagar, pois a data de emissão é maior que a data de vencimento.");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // ATUALIZA UMA CONTA A PAGAR
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ContasPagar contasPagar) {
        try {
            // Verifica se a conta a pagar existe
            if (!contaPagarService.findById(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }

            // Verificando se o fornecedor existe
            if (contasPagar.getFornecedor() == null || 
                !fornecedorService.existsById(contasPagar.getFornecedor().getId())) {
                return ResponseEntity.badRequest().body("Fornecedor não encontrado. Verifique se o ID do fornecedor está correto.");
            }

            // Verificando se o valor é positivo
            if (contasPagar.getValor().compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("O valor da conta a pagar deve ser positivo.");
            }

            contasPagar.setId(id); // Define o ID da conta a pagar
            ContasPagar updated = contaPagarService.save(contasPagar);
            return ResponseEntity.ok(updated);

        } catch (ResponseStatusException e) {
            // Captura o erro de validação de data e retorna uma resposta personalizada
            if (e.getMessage().contains("A data de emissão não pode ser posterior")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Não foi possível atualizar a conta a pagar, pois a data de emissão é maior que a data de vencimento.");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // EXCLUI UMA CONTA A PAGAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!contaPagarService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        contaPagarService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
