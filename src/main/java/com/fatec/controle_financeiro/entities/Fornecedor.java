package com.fatec.controle_financeiro.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fornecedores")
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;

    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContasPagar> contasPagar = new ArrayList<>(); // Inicializa a lista

    // Construtor padrão
    public Fornecedor() {
    }

    public Fornecedor(Long id, String name) { // Alterado para Long
        this.id = id;
        this.name = name;
    }

    // Getters e Setters
    public Long getId() { // Alterado para Long
        return id;
    }

    public void setId(Long id) { // Alterado para Long
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ContasPagar> getContasPagar() {
        return contasPagar; // Retorna a lista de contas a pagar
    }

    public void setContasPagar(List<ContasPagar> contasPagar) {
        this.contasPagar = contasPagar;
    }

    // Método para adicionar uma conta a pagar
    public void addContaPagar(ContasPagar contaPagar) {
        contasPagar.add(contaPagar);
        contaPagar.setFornecedor(this); // Define o fornecedor na conta a pagar
    }

    // Método para remover uma conta a pagar
    public void removeContaPagar(ContasPagar contaPagar) {
        contasPagar.remove(contaPagar);
        contaPagar.setFornecedor(null); // Remove a referência ao fornecedor
    }
}
