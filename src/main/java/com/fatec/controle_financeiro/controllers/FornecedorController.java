package com.fatec.controle_financeiro.controllers;

import com.fatec.controle_financeiro.domain.fornecedor.FornecedorRepository;
import com.fatec.controle_financeiro.entities.Fornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    // CREATE    
    @PostMapping
    public ResponseEntity<Fornecedor> create(@RequestBody Fornecedor fornecedor) {
        Fornecedor created = fornecedorRepository.save(fornecedor);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // READ - Lista todos os fornecedores
    @GetMapping
    public ResponseEntity<List<Fornecedor>> getAllFornecedor() {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        return new ResponseEntity<>(fornecedores, HttpStatus.OK);
    }

    // READ - Obtem fornecedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) { // Alterado para Long
        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(id);
        if (fornecedor.isPresent()) {
            return new ResponseEntity<>(fornecedor.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado");
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFornecedor(@PathVariable Long id, @RequestBody Fornecedor entity) { // Alterado para Long
        Optional<Fornecedor> fornecedorAtual = fornecedorRepository.findById(id);
        if (fornecedorAtual.isPresent()) {
            entity.setId(id);
            Fornecedor updatedFornecedor = fornecedorRepository.save(entity);
            return ResponseEntity.ok(updatedFornecedor); // Retorna 200 OK com o fornecedor atualizado
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado");
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFornecedor(@PathVariable Long id) { // Alterado para Long
        Optional<Fornecedor> fornecedorAtual = fornecedorRepository.findById(id);
        if (fornecedorAtual.isPresent()) {
            fornecedorRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado");
        }
    }
}
