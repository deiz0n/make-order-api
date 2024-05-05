package com.deiz0n.makeorderapi.domain.entities;

import com.deiz0n.makeorderapi.domain.enums.FormaPagamento;
import com.deiz0n.makeorderapi.domain.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id;
    @Column(nullable = false)
    private Instant data;
    @Column(nullable = false)
    @JsonProperty(value = "forma_pagamento")
    private FormaPagamento formaPagamento;
    @Column(nullable = false)
    private StatusPedido status;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer codigo;
    @Column(nullable = false, columnDefinition = "text")
    private String observacoes;

    @OneToMany(mappedBy = "pedido")
    private List<ItensPedido> itens;
    @ManyToOne
    private Comanda comanda;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    private Funcionario funcionario;
    @ManyToOne
    private Mesa mesa;

    @JsonProperty(value = "valor_total")
    public Double getValorTotal() {
        var soma = 0.0;
        for (ItensPedido iten : itens) {
            if (iten.getItem().getPreco() != null) soma += iten.getQuantidade() * iten.getItem().getPreco().doubleValue();
        }
        return soma;
    }

}
