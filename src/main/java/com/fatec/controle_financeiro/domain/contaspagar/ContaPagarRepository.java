package com.fatec.controle_financeiro.domain.contaspagar;

import com.fatec.controle_financeiro.entities.ContasPagar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaPagarRepository extends JpaRepository<ContasPagar, Long> {
}
