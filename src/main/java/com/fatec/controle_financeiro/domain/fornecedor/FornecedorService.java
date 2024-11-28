package com.fatec.controle_financeiro.domain.fornecedor;

import com.fatec.controle_financeiro.entities.Fornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public boolean existsById(Long id) {
        return fornecedorRepository.existsById(id);
    }

    public Fornecedor findById(Long id) {
        return fornecedorRepository.findById(id).orElse(null);
    }

    // Outros métodos relacionados ao fornecedor podem ser adicionados aqui
}
