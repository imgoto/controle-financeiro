package com.fatec.controle_financeiro.domain.fornecedor;

import com.fatec.controle_financeiro.entities.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Anotação para indicar que esta interface é um repositório
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    // Aqui você pode adicionar métodos personalizados, se necessário
    // Exemplo:
    // Optional<Fornecedor> findByName(String name);
}
