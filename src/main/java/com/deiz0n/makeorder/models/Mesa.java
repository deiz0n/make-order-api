package com.deiz0n.makeorder.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "tb_mesa")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(unique = true, nullable = false)
    private Integer numero;
    @Column(unique = true, nullable = false, length = 75)
    private String cliente;
    @Column(nullable = false, columnDefinition = "text")
    private String observacoes;

    @OneToMany(mappedBy = "mesa")
    private List<Pedido> pedidos;
}
