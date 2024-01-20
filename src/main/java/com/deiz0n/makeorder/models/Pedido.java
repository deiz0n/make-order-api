package com.deiz0n.makeorder.models;

import com.deiz0n.makeorder.models.enums.FormaPagamento;
import com.deiz0n.makeorder.models.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private Instant data;
    private FormaPagamento formaPagamento;
    private StatusPedido statusPedido;

    @ManyToOne
    private Comanda comanda;
    @ManyToOne
    private Mesa mesa;
    @ManyToOne
    private Funcionario funcionario;
}
