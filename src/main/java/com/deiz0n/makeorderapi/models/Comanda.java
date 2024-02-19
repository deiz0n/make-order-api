package com.deiz0n.makeorderapi.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "tb_comanda")
public class Comanda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "comanda")
    private List<Pedido> pedidos;
}
