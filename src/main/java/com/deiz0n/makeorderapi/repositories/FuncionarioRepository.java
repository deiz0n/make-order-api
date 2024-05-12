package com.deiz0n.makeorderapi.repositories;

import com.deiz0n.makeorderapi.domain.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {

    Optional<Funcionario> findByCpf(String cpf);
    Optional<Funcionario> findByEmail(String email);

}
