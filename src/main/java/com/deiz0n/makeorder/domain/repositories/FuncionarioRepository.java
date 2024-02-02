package com.deiz0n.makeorder.domain.repositories;

import com.deiz0n.makeorder.domain.models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {
}
