package com.fatec.controle_financeiro.domain.ContasReceber;

import com.fatec.controle_financeiro.entities.ContasReceber;
import com.fatec.controle_financeiro.entities.Cliente;
import com.fatec.controle_financeiro.domain.cliente.ClienteRepository;  // Importando o repositório de Cliente
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ContaReceberService {

    @Autowired
    private ContasReceberRepository contaReceberRepository;

    @Autowired
    private ClienteRepository clienteRepository;  // Repositório de Cliente

    public List<ContasReceber> listarTodas() {
        return contaReceberRepository.findAll();
    }

    public Optional<ContasReceber> buscarPorId(Long id) {
        return contaReceberRepository.findById(id);
    }

    public ContasReceber criar(ContasReceber contaReceber) {
        // Verifica se o cliente existe
        Cliente cliente = clienteRepository.findById(contaReceber.getCliente().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        contaReceber.setCliente(cliente); // Associa o cliente
        validarDados(contaReceber); // Valida os dados antes de salvar
        return contaReceberRepository.save(contaReceber);
    }

    public ContasReceber atualizar(Long id, ContasReceber contaReceber) {
        if (!contaReceberRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta a receber não encontrada");
        }

        // Verifica se o cliente existe
        Cliente cliente = clienteRepository.findById(contaReceber.getCliente().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        contaReceber.setCliente(cliente); // Associa o cliente
        validarDados(contaReceber); // Valida os dados antes de salvar
        return contaReceberRepository.save(contaReceber);
    }

    public void deletar(Long id) {
        if (!contaReceberRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta a receber não encontrada");
        }
        contaReceberRepository.deleteById(id);
    }

    private void validarDados(ContasReceber contaReceber) {
        if (contaReceber.getEmissao().isAfter(contaReceber.getVencimento())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A data de emissão não pode ser posterior à data de vencimento");
        }
        if (contaReceber.getValor() == null || contaReceber.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor da conta a receber deve ser positivo");
        }
    }
}
