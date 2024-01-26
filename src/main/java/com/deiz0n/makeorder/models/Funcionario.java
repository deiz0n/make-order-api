package com.deiz0n.makeorder.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

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
    @Column(unique = true, nullable = false, length = 100)
    private String nome;
    @Column(unique = true, nullable = false, length = 11)
    private String cpf;
    @Column(unique = true, nullable = false, length = 50)
    private String email;
    @Column(nullable = false, length = 30)
    private String senha;
    @Column(nullable = false)
    private Date dataNascimento;
    @Column(nullable = false, length = 50)
    private String cargo;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "funcionario")
    private List<Pedido> pedidos;
    @Setter(AccessLevel.NONE)
    @ManyToMany(mappedBy = "funcionarios")
    private List<Permissao> permissoes;

}
