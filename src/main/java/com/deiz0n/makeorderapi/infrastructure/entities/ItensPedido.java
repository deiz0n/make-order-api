package com.deiz0n.makeorderapi.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_itens_pedido")
public class ItensPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Integer quantidade;

    @ManyToOne
    private Item item;
    @JsonIgnore
    @ManyToOne
    private Pedido pedido;
}

