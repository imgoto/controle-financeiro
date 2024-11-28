package com.fatec.controle_financeiro.domain.Categoria;

import com.fatec.controle_financeiro.entities.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria criarCategoria(Categoria categoria) {
        if (categoriaRepository.findByDescricao(categoria.getDescricao()).isPresent()) {
            throw new RuntimeException("ERRO: Já existe uma categoria com esta descrição!");
        }
        if (categoria.getAtivo() == null) {
            categoria.setAtivo(true);
        }
        return categoriaRepository.save(categoria);
    }
}
