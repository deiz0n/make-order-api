package com.deiz0n.makeorderapi.infrastructure.entities;

import com.deiz0n.makeorderapi.core.domain.enums.Setor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "tb_funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id;
    @Column(nullable = false, length = 100)
    private String nome;
    @Column(unique = true, nullable = false, length = 11)
    private String cpf;
    @Column(unique = true, nullable = false, length = 50)
    private String email;
    @Column(nullable = false, length = 100)
    private String senha;
    @JsonProperty(value = "data_nascimento")
    @Column(nullable = false)
    private Date dataNascimento;
    @Column(nullable = false)
    private Setor setor;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "funcionario")
    private List<Pedido> pedidos;
}
