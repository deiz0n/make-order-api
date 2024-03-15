package com.deiz0n.makeorderapi.domain.services;

import com.deiz0n.makeorderapi.domain.dto.FuncionarioDTO;
import com.deiz0n.makeorderapi.domain.models.Funcionario;
import com.deiz0n.makeorderapi.domain.models.enums.Cargo;
import com.deiz0n.makeorderapi.domain.repositories.FuncionarioRepository;
import com.deiz0n.makeorderapi.domain.services.exceptions.ExistingFieldException;
import com.deiz0n.makeorderapi.domain.utils.MokTopFuncionarios;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository repository;
    private final ModelMapper mapper;
    @Value("${api.security.token.secret}")
    private String secret;

    public FuncionarioService(FuncionarioRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<FuncionarioDTO> getResouces() {
        return repository.findAll()
                .stream()
                .map(x -> mapper.map(x, FuncionarioDTO.class))
                .toList();
    }


    public FuncionarioDTO getResourceByToken(@AuthenticationPrincipal Funcionario funcionario) {
        return mapper.map(funcionario, FuncionarioDTO.class);
    }

    public List<MokTopFuncionarios> getTopFuncionarios() {
        return List.of(
                new MokTopFuncionarios("ADMIN", Cargo.ADMINISTRADOR, 18),
                new MokTopFuncionarios("Dudu", Cargo.GARCOM, 15),
                new MokTopFuncionarios("Leandro", Cargo.GARCOM, 10)
        );
    }

    public FuncionarioDTO createResource(FuncionarioDTO newFuncionario) {
        dataValidation(newFuncionario);
        var funcionario = mapper.map(newFuncionario, Funcionario.class);
        var encodeSenha =  new BCryptPasswordEncoder().encode(funcionario.getSenha());
        funcionario.setSenha(encodeSenha);
        repository.save(funcionario);
        return newFuncionario;
    }

    public void dataValidation(FuncionarioDTO newFuncionario) {
        if (repository.findFirstByEmail(newFuncionario.getEmail()) != null) throw new ExistingFieldException("Email já cadastrado");
        if (repository.findFirstByCpf(newFuncionario.getCpf()).isPresent()) throw new ExistingFieldException("CPF já cadastrado");
    }



}
