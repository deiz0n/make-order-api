package com.deiz0n.makeorder.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "tb_permissao")
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id;
    @Column(unique = true, nullable = false, length = 50)
    private String nome;
    @Column(nullable = false, columnDefinition = "text")
    private String descricao;

    @Setter(AccessLevel.NONE)
    @ManyToMany
    @JoinTable(name = "tb_permissao_funcionario",
            joinColumns = @JoinColumn(name = "permissao_id"),
            inverseJoinColumns = @JoinColumn(name = "funcionario_id"))
    private List<Funcionario> funcionarios;
}
