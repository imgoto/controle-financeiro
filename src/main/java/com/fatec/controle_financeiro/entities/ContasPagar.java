package com.fatec.controle_financeiro.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contas_pagar")
public class ContasPagar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emissao")
    private LocalDate emissao;

    @Column(name = "vencimento")
    private LocalDate vencimento;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    @JsonIgnoreProperties("contasPagar") // Ignora a propriedade de volta
    private Fornecedor fornecedor;

    @Column(name = "valor_a_pagar", precision = 12, scale = 2) // Ajuste no nome da coluna
    private BigDecimal valor;

    // Construtor padrão
    public ContasPagar() {
    }

    public ContasPagar(Long id, LocalDate emissao, LocalDate vencimento, Fornecedor fornecedor, BigDecimal valor) {
        this.id = id;
        this.emissao = emissao;
        this.vencimento = vencimento;
        this.fornecedor = fornecedor;
        this.valor = valor;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEmissao() {
        return emissao;
    }

    public void setEmissao(LocalDate emissao) {
        this.emissao = emissao;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    // Adicionando um método para obter o ID do fornecedor
    public Long getFornecedorId() {
        return fornecedor != null ? fornecedor.getId() : null; // Retorna o ID do fornecedor, se disponível
    }
}
