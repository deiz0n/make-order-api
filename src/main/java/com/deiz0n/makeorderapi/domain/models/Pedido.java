package com.deiz0n.makeorderapi.domain.models;

import com.deiz0n.makeorderapi.domain.models.enums.FormaPagamento;
import com.deiz0n.makeorderapi.domain.models.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private FormaPagamento formaPagamento;
    @Column(nullable = false)
    private StatusPedido statusPedido;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer codigo;

    @ManyToOne
    private Comanda comanda;
    @ManyToOne
    private Mesa mesa;
    @JsonIgnore
    @ManyToOne
    private Funcionario funcionario;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_item_pedido",
            joinColumns = @JoinColumn(name = "id_pedido"),
            inverseJoinColumns = @JoinColumn(name = "id_item")
    )
    private List<Item> itens;

}
