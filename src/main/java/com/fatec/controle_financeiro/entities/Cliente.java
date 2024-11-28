package com.fatec.controle_financeiro.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("cliente") // Ignorar o campo cliente ao serializar ContasReceber
    private List<ContasReceber> contasReceber = new ArrayList<>();

    // Construtores, Getters e Setters
    public Cliente() {}

    public Cliente(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ContasReceber> getContasReceber() {
        return contasReceber;
    }

    public void setContasReceber(List<ContasReceber> contasReceber) {
        this.contasReceber = contasReceber;
    }
}
