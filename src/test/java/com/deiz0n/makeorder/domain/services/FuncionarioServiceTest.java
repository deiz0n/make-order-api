package com.deiz0n.makeorder.domain.services;

import com.deiz0n.makeorder.domain.dtos.FuncionarioDTO;
import com.deiz0n.makeorder.domain.models.Funcionario;
import com.deiz0n.makeorder.domain.models.Pedido;
import com.deiz0n.makeorder.domain.models.enums.Cargo;
import com.deiz0n.makeorder.domain.repositories.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class FuncionarioServiceTest {

    public static final UUID ID = UUID.randomUUID();
    public static final String NOME = "Eduardo";
    public static final String CPF = "123456789";
    public static final String EMAIL = "eduardo@gmail.com";
    public static final String SENHA = "123";
    public static final Date DATA = Date.from(Instant.now());
    public static final String CARGO = "Garçom";
    public static final int INDEX = 0;

    @InjectMocks
    private FuncionarioService service;
    @Mock
    private FuncionarioRepository repository;
    @Mock
    private ModelMapper mapper;

    private Funcionario funcionario;
    private FuncionarioDTO funcionarioDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        starFuncionario();
    }

    @Test
    void whenGetResoucesThenReturnListOfFuncionarioDTO() {
        when(repository.findAll()).thenReturn(List.of(funcionario));
        when(mapper.map(any(), any())).thenReturn(funcionarioDTO);

        List<FuncionarioDTO> response = service.getResouces();

        assertNotNull(response);

        assertEquals(1, response.size());
        assertEquals(FuncionarioDTO.class, response.get(INDEX).getClass());

        assertEquals(NOME, response.get(INDEX).getNome());
        assertEquals(CPF, response.get(INDEX).getCpf());
        assertEquals(EMAIL, response.get(INDEX).getEmail());
        assertEquals(SENHA, response.get(INDEX).getSenha());
        assertEquals(DATA, response.get(INDEX).getDataNascimento());
        assertEquals(CARGO, response.get(INDEX).getCargo());
    }

    @Test
    void whenCreateResourceThenReturnFuncionarioDTO() {
        when(repository.save(any())).thenReturn(funcionario);
        when(mapper.map(any(), any())).thenReturn(funcionario );

        FuncionarioDTO response = service.createResource(funcionarioDTO);

        assertEquals(FuncionarioDTO.class, response.getClass());

        assertEquals(NOME, response.getNome());
        assertEquals(CPF, response.getCpf());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(SENHA, response.getSenha());
        assertEquals(DATA, response.getDataNascimento());
        assertEquals(CARGO, response.getCargo());
    }

    private void starFuncionario() {
        funcionario = new Funcionario(ID
                ,NOME
                ,CPF
                ,EMAIL
                ,SENHA
                ,DATA
                ,Cargo.ADMINISTRADOR
                ,List.of(new Pedido()));
        funcionarioDTO = new FuncionarioDTO(ID
                ,NOME
                ,CPF
                ,EMAIL
                ,SENHA
                ,DATA
                , Cargo.ADMINISTRADOR);
    }
}