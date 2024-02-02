package com.deiz0n.makeorder.domain.services;

import com.deiz0n.makeorder.domain.dtos.FuncionarioDTO;
import com.deiz0n.makeorder.domain.models.Funcionario;
import com.deiz0n.makeorder.domain.repositories.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    private FuncionarioRepository funcionarioRepository;
    private ModelMapper mapper;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, ModelMapper mapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.mapper = mapper;
    }

    public List<FuncionarioDTO> getResouces() {
        List<FuncionarioDTO> funcionarios = funcionarioRepository.findAll()
                .stream()
                .map(x -> mapper.map(x, FuncionarioDTO.class))
                .toList();
        return funcionarios;
    }

    public FuncionarioDTO createResource(FuncionarioDTO newFuncionario) {
        var funcionario = mapper.map(newFuncionario, Funcionario.class);
        funcionarioRepository.save(funcionario);
        return newFuncionario;
    }
}
