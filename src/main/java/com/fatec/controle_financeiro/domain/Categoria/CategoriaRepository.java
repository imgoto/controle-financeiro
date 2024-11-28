package com.fatec.controle_financeiro.domain.Categoria;

import com.fatec.controle_financeiro.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByDescricao(String descricao);
}
