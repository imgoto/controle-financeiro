package com.fatec.controle_financeiro.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contas_receber")
public class ContasReceber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emissao", nullable = false)
    private LocalDate emissao;

    @Column(name = "vencimento", nullable = false)
    private LocalDate vencimento;

    @ManyToOne(fetch = FetchType.EAGER) // Ensure client is loaded
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnoreProperties("contasReceber")
    private Cliente cliente;

    @Column(name = "valor", precision = 12, scale = 2, nullable = false)
    private BigDecimal valor;

    // Construtores, Getters e Setters
    public ContasReceber() {}

    public ContasReceber(LocalDate emissao, LocalDate vencimento, BigDecimal valor, Cliente cliente) {
        this.emissao = emissao;
        this.vencimento = vencimento;
        this.valor = valor;
        this.cliente = cliente;
    }

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
