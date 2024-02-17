package com.deiz0n.makeorder.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "tb_item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id;
    @Column(unique = true, nullable = false, length = 50)
    private String nome;
    @Column(nullable = false)
    private BigDecimal preco;
    @Column(nullable = false, columnDefinition = "text")
    private String descricao;
    @Column(nullable = false)
    private Integer quantidade;

    @ManyToOne
    private Categoria categoria;
    @ManyToMany
    @JoinTable(
            name = "tb_itens_pedidos",
            joinColumns = @JoinColumn(name = "id_item"),
            inverseJoinColumns = @JoinColumn(name = "id_pedido")
    )
    private List<Pedido> pedido;
}
