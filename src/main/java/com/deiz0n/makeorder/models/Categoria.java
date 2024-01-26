package com.deiz0n.makeorder.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "tb_categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    private String nome;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "categoria")
    private List<Item> items;
}
