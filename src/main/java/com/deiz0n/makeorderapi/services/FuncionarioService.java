package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.dtos.FuncionarioDTO;
import com.deiz0n.makeorderapi.domain.entities.Funcionario;
import com.deiz0n.makeorderapi.domain.events.SendEmailEvent;
import com.deiz0n.makeorderapi.domain.exceptions.FuncionarioExistingException;
import com.deiz0n.makeorderapi.domain.exceptions.FuncionarioNotFoundException;
import com.deiz0n.makeorderapi.domain.utils.Mensagem;
import com.deiz0n.makeorderapi.repositories.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    private FuncionarioRepository funcionarioRepository;
    private ModelMapper mapper;
    private BCryptPasswordEncoder cryptPasswordEncoder;
    private ApplicationEventPublisher eventPublisher;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, ModelMapper mapper, BCryptPasswordEncoder cryptPasswordEncoder, ApplicationEventPublisher eventPublisher) {
        this.funcionarioRepository = funcionarioRepository;
        this.mapper = mapper;
        this.cryptPasswordEncoder = cryptPasswordEncoder;
        this.eventPublisher = eventPublisher;
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
                .orElseThrow(() -> new FuncionarioNotFoundException("Não foi possível encontrar um funcionário com o Id informado"));
    }

    public FuncionarioDTO create(Funcionario newFuncionario) {
        if (funcionarioRepository.findByCpf(newFuncionario.getCpf()).isPresent()) throw new FuncionarioExistingException("CPF já vinculado a um funcionário");
        if (funcionarioRepository.findByEmail(newFuncionario.getEmail()).isPresent()) throw new FuncionarioExistingException("Email já vinculado a um funcionário");

        newFuncionario.setSenha(cryptPasswordEncoder.encode(newFuncionario.getSenha()));

        funcionarioRepository.save(newFuncionario);
        return mapper.map(newFuncionario, FuncionarioDTO.class);
    }

    public void delete(UUID id) {
        var funcionario = getById(id);
        funcionarioRepository.deleteById(funcionario.getId());
    }

    public FuncionarioDTO update(UUID id, Funcionario newData) {
        try {
            var funcionario = funcionarioRepository.getReferenceById(id);

            var getByCpf = funcionarioRepository.findByCpf(newData.getCpf());
            if (getByCpf.isPresent() && !getByCpf.get().getId().equals(id))
                throw new FuncionarioExistingException("CPF já vinculado a um funcionário");

            var getByEmail = funcionarioRepository.findByEmail(newData.getEmail());
            if (getByEmail.isPresent() && !getByEmail.get().getId().equals(id))
                throw new FuncionarioExistingException("Email já vinculado a um funcionário");

            BeanUtils.copyProperties(newData, funcionario, "id", "dataNascimento");

            funcionario.setSenha(cryptPasswordEncoder.encode(funcionario.getSenha()));

            funcionarioRepository.save(funcionario);
            return mapper.map(funcionario, FuncionarioDTO.class);
        } catch (FatalBeanException | IllegalArgumentException e) {
            throw new FuncionarioNotFoundException("Não foi possível encontrar um funcionário com o Id informado");
        }
    }

    public void recovery(String email) {
        var user = funcionarioRepository.findByEmail(email);

        if (user.isEmpty())
            throw new FuncionarioNotFoundException("Não foi possível encontrar um funcionário com o email informado");

        var mensagem = new Mensagem(
                user.get().getEmail(),
                "Recuperação da senha",
                "Teste"
        );
        var sendEmail = new SendEmailEvent(this, mensagem);
        eventPublisher.publishEvent(sendEmail);
    }
}
