package com.deiz0n.makeorder.domain.models;

import com.deiz0n.makeorder.domain.models.enums.FormaPagamento;
import com.deiz0n.makeorder.domain.models.enums.StatusPedido;
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

    @ManyToOne
    private Comanda comanda;
    @ManyToOne
    private Mesa mesa;
    @ManyToOne
    private Funcionario funcionario;
    @ManyToMany(mappedBy = "pedido")
    private List<Item> itens;

    public Double valorTotal() {
        var soma = 0.0;
        for (Item x : itens) {
            soma += x.getPreco().doubleValue() * x.getQuantidade();
        }
        return soma;
    }

}
