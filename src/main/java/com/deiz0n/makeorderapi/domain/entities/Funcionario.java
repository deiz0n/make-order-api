package com.deiz0n.makeorderapi.domain.entities;

import com.deiz0n.makeorderapi.domain.enums.Setor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "tb_funcionario")
public class Funcionario implements UserDetails {

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
    @Column(nullable = false)
    private Date dataNascimento;
    @Column(nullable = false)
    private Setor setor;
    @Column(name = "sub_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID subId;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "funcionario")
    private List<Pedido> pedidos;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (setor == Setor.ADMINISTRACAO)
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMINISTRACAO"),
                    new SimpleGrantedAuthority("ROLE_GARCOM"),
                    new SimpleGrantedAuthority("ROLE_COZINHA")
            );
        else if (setor == Setor.GARCOM) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_GARCOM"),
                    new SimpleGrantedAuthority("ROLE_COZINHA")
            );
        } else
            return List.of(
                    new SimpleGrantedAuthority("ROLE_COZINHA")
            );
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
