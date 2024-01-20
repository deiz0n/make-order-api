package com.deiz0n.makeorder.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "tb_funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private Date dataNascimento;
    private String cargo;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "funcionario")
    private List<Pedido> pedidos;

}
