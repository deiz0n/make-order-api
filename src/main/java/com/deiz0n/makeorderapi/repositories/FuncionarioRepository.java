package com.deiz0n.makeorderapi.repositories;

import com.deiz0n.makeorderapi.models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {

    UserDetails findFirstByEmail(String email);
    Optional<Funcionario> findFirstByCpf(String cpf);

}
