package com.fatec.controle_financeiro.controllers;

import com.fatec.controle_financeiro.entities.Categoria;
import com.fatec.controle_financeiro.domain.Categoria.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategorias() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> criarCategoria(@RequestBody Categoria categoria) {
    
        Optional<Categoria> categoriaExistente = categoriaRepository.findByDescricao(categoria.getDescricao());
        
        if (categoriaExistente.isPresent()) {
            return ResponseEntity.status(400).body("ERRO: A descrição da categoria já está sendo usada!");
        }
        
        Categoria novaCategoria = categoriaRepository.save(categoria);
        
        return ResponseEntity.ok(novaCategoria);
    }
}
