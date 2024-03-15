package com.deiz0n.makeorderapi.domain.models;

import com.deiz0n.makeorderapi.domain.models.enums.Cargo;
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
    private Cargo cargo;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "funcionario")
    private List<Pedido> pedidos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.cargo == Cargo.ADMINISTRADOR) return List.of(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"), new SimpleGrantedAuthority("ROLE_GARCOM"));
        if(this.cargo == Cargo.GARCOM) return List.of(new SimpleGrantedAuthority("ROLE_GARCOM"));
        else return List.of(new SimpleGrantedAuthority("ROLE_COZINHEIRO"));
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
