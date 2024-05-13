package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.dtos.FuncionarioDTO;
import com.deiz0n.makeorderapi.domain.entities.Funcionario;
import com.deiz0n.makeorderapi.domain.exceptions.FuncionarioExistingException;
import com.deiz0n.makeorderapi.domain.exceptions.FuncionarioNotFoundException;
import com.deiz0n.makeorderapi.repositories.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    private FuncionarioRepository funcionarioRepository;
    private ModelMapper mapper;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, ModelMapper mapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.mapper = mapper;
    }

    public List<FuncionarioDTO> getAll() {
        return funcionarioRepository.findAll()
                .stream()
                .map(funcionario -> mapper.map(funcionario, FuncionarioDTO.class))
                .collect(Collectors.toList());
    }

    public FuncionarioDTO getById(UUID id) {
        return funcionarioRepository.findById(id)
                .map(funcionario -> mapper.map(funcionario, FuncionarioDTO.class))
                .orElseThrow(() -> new FuncionarioNotFoundException("Funcionário não encontradp"));
    }

    public FuncionarioDTO create(Funcionario newFuncionario) {
        if (funcionarioRepository.findByCpf(newFuncionario.getCpf()).isPresent()) throw new FuncionarioExistingException("CPF já vinculado a um funcionário");
        if (funcionarioRepository.findByEmail(newFuncionario.getEmail()).isPresent()) throw new FuncionarioExistingException("Email já vinculado a um funcionário");
        funcionarioRepository.save(newFuncionario);
        return mapper.map(newFuncionario, FuncionarioDTO.class);
    }

    public void delete(UUID id) {
        var funcionario = getById(id);
        funcionarioRepository.deleteById(funcionario.getId());
    }

    public FuncionarioDTO update(UUID id, Funcionario newData) {
        var funcionario = getById(id);
        if (funcionarioRepository.findByCpf(newData.getCpf()).isPresent() && !funcionario.getId().equals(funcionario.getId()))
            throw new FuncionarioExistingException("CPF já vinculado a um funcionário");
        if (funcionarioRepository.findByEmail(newData.getEmail()).isPresent() && !funcionario.getId().equals(funcionario.getId()))
            throw new FuncionarioExistingException("Email já vinculado a um funcionário");
        BeanUtils.copyProperties(newData, funcionario, "id", "dataNascimento");
        return mapper.map(newData, FuncionarioDTO.class);
    }
}
