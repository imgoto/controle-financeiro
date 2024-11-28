package com.fatec.controle_financeiro.domain.contaspagar;

import com.fatec.controle_financeiro.entities.ContasPagar;
import com.fatec.controle_financeiro.entities.Fornecedor;
import com.fatec.controle_financeiro.domain.fornecedor.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ContaPagarService {

    @Autowired
    private ContaPagarRepository contaPagarRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public List<ContasPagar> findAll() {
        return contaPagarRepository.findAll();
    }

    public Optional<ContasPagar> findById(Long id) {
        return contaPagarRepository.findById(id);
    }

    public ContasPagar save(ContasPagar contasPagar) {
        validarDatas(contasPagar); // Valida as datas antes de qualquer outra coisa
        verificarFornecedor(contasPagar); // Verifica se o fornecedor existe
    
        return contaPagarRepository.save(contasPagar);
    }

    public ContasPagar update(Long id, ContasPagar contasPagar) {
        if (!contaPagarRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta a pagar não encontrada");
        }

        validarDatas(contasPagar); // Valida as datas antes de atualizar
        verificarFornecedor(contasPagar); // Verifica se o fornecedor existe

        return contaPagarRepository.save(contasPagar);
    }

    public void deleteById(Long id) {
        if (!contaPagarRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta a pagar não encontrada");
        }
        contaPagarRepository.deleteById(id);
    }

    private void validarDatas(ContasPagar contasPagar) {
        // Se a data de emissão for posterior à data de vencimento, lançar a exceção com mensagem personalizada
        if (contasPagar.getEmissao().isAfter(contasPagar.getVencimento())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "A data de emissão não pode ser posterior à data de vencimento");
        }
    }

    private void verificarFornecedor(ContasPagar contasPagar) {
        // Verifica se o fornecedor existe antes de associá-lo à conta a pagar
        Fornecedor fornecedor = fornecedorRepository.findById(contasPagar.getFornecedor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                        "Fornecedor não encontrado"));
        contasPagar.setFornecedor(fornecedor); // Associa o fornecedor à conta a pagar
    }
}
