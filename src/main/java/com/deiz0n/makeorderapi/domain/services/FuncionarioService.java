package com.deiz0n.makeorderapi.domain.services;

import com.deiz0n.makeorderapi.domain.dto.FuncionarioDTO;
import com.deiz0n.makeorderapi.domain.models.Funcionario;
import com.deiz0n.makeorderapi.domain.repositories.FuncionarioRepository;
import com.deiz0n.makeorderapi.domain.services.exceptions.ExistingFieldException;
import com.deiz0n.makeorderapi.domain.utils.CustomEvent;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository repository;
    private final ModelMapper mapper;

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

//    @EventListener
//    public Object getResourceByToken(CustomEvent event) {
//        return event;
//    }

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
