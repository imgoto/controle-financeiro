package com.fatec.controle_financeiro.domain.ContasReceber;

import com.fatec.controle_financeiro.entities.ContasReceber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContasReceberRepository extends JpaRepository<ContasReceber, Long> {
}
