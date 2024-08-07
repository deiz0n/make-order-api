package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.dtos.FuncionarioDTO;
import com.deiz0n.makeorderapi.domain.dtos.NewFuncionarioDTO;
import com.deiz0n.makeorderapi.domain.entities.Funcionario;
import com.deiz0n.makeorderapi.domain.enums.Setor;
import com.deiz0n.makeorderapi.domain.events.ResetedPasswordEvent;
import com.deiz0n.makeorderapi.domain.events.SendEmailEvent;
import com.deiz0n.makeorderapi.domain.exceptions.FuncionarioExistingException;
import com.deiz0n.makeorderapi.domain.exceptions.FuncionarioNotFoundException;
import com.deiz0n.makeorderapi.domain.exceptions.PasswordNotEqualsException;
import com.deiz0n.makeorderapi.domain.utils.requests.ResetPasswordRequest;
import com.deiz0n.makeorderapi.repositories.FuncionarioRepository;
import com.deiz0n.makeorderapi.repositories.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class FuncionarioServiceTest {

    public static final Integer INDEX = 0;
    public static final UUID ID = UUID.randomUUID();
    public static final String NOME = "Funcionário 1";
    public static final String CPF = "55967363092";
    public static final String EMAIL = "email@gmail.com";
    public static final String SENHA = "123";
    public static final Date DATA_NASCIMENTO = Date.from(Instant.now());
    public static final Setor SETOR = Setor.ADMINISTRACAO;

    @InjectMocks
    private FuncionarioService funcionarioService;
    @Mock
    private FuncionarioRepository funcionarioRepository;
    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private ModelMapper mapper;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Mock
    private Authentication authentication;

    private Funcionario funcionario;
    private FuncionarioDTO funcionarioDTO;
    private NewFuncionarioDTO newFuncionarioDTO;
    private Optional<Funcionario> optional;
    private ResetedPasswordEvent passwordEvent;
    private SendEmailEvent emailEvent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockData();
    }

    @Test
    void whenGetAllThenReturnListOfFuncionarioDTO() {
        when(funcionarioRepository.findAll()).thenReturn(List.of(funcionario));
        when(mapper.map(any(), any())).thenReturn(funcionarioDTO);

        List<FuncionarioDTO> responseList = funcionarioService.getAll();

        assertNotNull(responseList);
        assertEquals(ArrayList.class, responseList.getClass());
        assertEquals(FuncionarioDTO.class, responseList.get(INDEX).getClass());

        assertEquals(ID, responseList.get(INDEX).getId());
        assertEquals(NOME, responseList.get(INDEX).getNome());
        assertEquals(EMAIL, responseList.get(INDEX).getEmail());
        assertEquals(DATA_NASCIMENTO, responseList.get(INDEX).getDataNascimento());
        assertEquals(SETOR, responseList.get(INDEX).getSetor());
    }

    @Test
    void whenGetTopThenReturnListOfObject() {
        when(pedidoRepository.getTopFuncionaios()).thenReturn(List.of(new Object()));

        List<Object> responseList = funcionarioService.getTop();

        assertNotNull(responseList);
        assertEquals(Object.class, responseList.get(INDEX).getClass());
    }

    @Test
    void whenGetByIdThenReturnFuncionarioDTO() {
        when(funcionarioRepository.findById(any(UUID.class))).thenReturn(optional);
        when(mapper.map(any(), any())).thenReturn(funcionarioDTO);

        FuncionarioDTO response = funcionarioService.getById(ID);

        assertNotNull(response);
        assertEquals(FuncionarioDTO.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(DATA_NASCIMENTO, response.getDataNascimento());
        assertEquals(SETOR, response.getSetor());
    }

    @Test
    void whenGetByIdThenThrowFuncionarioNotFoundException() {
        when(funcionarioRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        var exception = assertThrows(
                FuncionarioNotFoundException.class,
                () -> funcionarioService.getById(ID)
        );

        assertEquals("Não foi possível encontrar um funcionário com o Id informado", exception.getMessage());
    }

    @Test
    void whenCreateThenReturnFuncionarioDTO() {
        when(funcionarioRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(funcionarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(mapper.map(any(), any())).thenReturn(funcionarioDTO);
        when(funcionarioRepository.save(any())).thenReturn(funcionario);

        FuncionarioDTO response = funcionarioService.create(newFuncionarioDTO);

        assertNotNull(response);
        assertEquals(FuncionarioDTO.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(DATA_NASCIMENTO, response.getDataNascimento());
        assertEquals(SETOR, response.getSetor());
    }

    @Test
    void whenCreateThenThrowFuncionarioExistingExceptionCausedByCPF() {
        when(funcionarioRepository.findByCpf(anyString())).thenReturn(optional);

        var exception = assertThrows(
                FuncionarioExistingException.class,
                () -> funcionarioService.create(newFuncionarioDTO)
        );

        assertEquals("CPF já vinculado a um funcionário", exception.getMessage());
    }

    @Test
    void whenCreateThenThrowFuncionarioExistingExceptionCausedByEmail() {
        when(funcionarioRepository.findByEmail(anyString())).thenReturn(optional);

        var exception = assertThrows(
                FuncionarioExistingException.class,
                () -> funcionarioService.create(newFuncionarioDTO)
        );

        assertEquals("Email já vinculado a um funcionário", exception.getMessage());
    }

    @Test
    void whenDeleteThenDontReturn() {
        when(funcionarioRepository.findById(any(UUID.class))).thenReturn(optional);
        when(mapper.map(any(), any())).thenReturn(funcionarioDTO);

        doNothing().when(funcionarioRepository).deleteById(any(UUID.class));

        funcionarioService.delete(ID);

        verify(funcionarioRepository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    void whenDeleteThenThrowFuncionarioNotFoundException() {
        when(funcionarioRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        var exception = assertThrows(
                FuncionarioNotFoundException.class,
                () -> funcionarioService.delete(ID)
        );

        assertEquals("Não foi possível encontrar um funcionário com o Id informado", exception.getMessage());
    }

    @Test
    void whenUpdateThenReturnFuncionarioDTO() {
        when(funcionarioRepository.getReferenceById(any(UUID.class))).thenReturn(funcionario);
        when(funcionarioRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(funcionarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(funcionarioRepository.save(any())).thenReturn(funcionario);
        when(mapper.map(any(), any())).thenReturn(funcionarioDTO);

        FuncionarioDTO response = funcionarioService.update(ID, newFuncionarioDTO);

        assertNotNull(response);
        assertEquals(FuncionarioDTO.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(DATA_NASCIMENTO, response.getDataNascimento());
        assertEquals(SETOR, response.getSetor());
    }

    @Test
    void whenUpdateThenThrowFuncionarNotFoundException() {
        when(funcionarioRepository.getReferenceById(any(UUID.class))).thenReturn(null);
        when(funcionarioRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(funcionarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        var exception = assertThrows(
                FuncionarioNotFoundException.class,
                () -> funcionarioService.update(ID, newFuncionarioDTO)
        );

        assertEquals("Não foi possível encontrar um funcionário com o Id informado", exception.getMessage());
    }

    @Test
    void whenUpdateThenThrowFuncionarioExistingExceptionCausedByCPF() {
        when(funcionarioRepository.getReferenceById(any(UUID.class))).thenReturn(funcionario);
        when(funcionarioRepository.findByCpf(anyString())).thenReturn(optional);

        funcionario.setId(UUID.randomUUID());

        var exception = assertThrows(
                FuncionarioExistingException.class,
                () -> funcionarioService.update(ID, newFuncionarioDTO)
        );

        assertEquals("CPF já vinculado a um funcionário", exception.getMessage());
    }

    @Test
    void whenUpdateThenThrowFuncionarioExistingExceptionCausedByEmail() {
        when(funcionarioRepository.getReferenceById(any(UUID.class))).thenReturn(funcionario);
        when(funcionarioRepository.findByEmail(anyString())).thenReturn(optional);

        funcionario.setId(UUID.randomUUID());

        var exception = assertThrows(
                FuncionarioExistingException.class,
                () -> funcionarioService.update(ID, newFuncionarioDTO)
        );

        assertEquals("Email já vinculado a um funcionário", exception.getMessage());
    }

    @Test
    void whenRecoveryThenDontReturn() {
        doNothing().when(eventPublisher).publishEvent(emailEvent);

        when(funcionarioRepository.findByEmail(anyString())).thenReturn(optional);
        when(mapper.map(any(), any())).thenReturn(funcionarioDTO);

        funcionarioService.recovery(EMAIL);

        verify(funcionarioRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void whenRecoveryThenThrowFuncionarioNotFoundException() {
        doNothing().when(eventPublisher).publishEvent(emailEvent);

        when(funcionarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(mapper.map(any(), any())).thenReturn(funcionarioDTO);

        var exception = assertThrows(
                FuncionarioNotFoundException.class,
                () -> funcionarioService.recovery(EMAIL)
        );

        assertEquals("Não foi possível encontrar um funcionário com o email informado", exception.getMessage());
    }

    @Test
    void whenGetBySessionThenReturnFuncionarioDTO() {
        when(mapper.map(any(), any())).thenReturn(funcionarioDTO);

        FuncionarioDTO response = funcionarioService.getBySession(authentication);

        assertNotNull(response);
        assertEquals(FuncionarioDTO.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(DATA_NASCIMENTO, response.getDataNascimento());
        assertEquals(SETOR, response.getSetor());
    }

    @Test
    void whenResetPasswordThenDontReturn() {
        doNothing().when(eventPublisher).publishEvent(passwordEvent);

        when(funcionarioRepository.findBySubId(any(UUID.class))).thenReturn(optional);
        when(funcionarioRepository.save(any())).thenReturn(funcionario);

        funcionarioService.resetPassword(passwordEvent);

        verify(funcionarioRepository, times(1)).findBySubId((UUID) passwordEvent.getSource());
        verify(funcionarioRepository, times(1)).save(funcionario);    }

    @Test
    void whenResetPasswordThenThrowPasswordNotEquals() {
        doNothing().when(eventPublisher).publishEvent(passwordEvent);

        when(funcionarioRepository.findBySubId(any(UUID.class))).thenReturn(optional);
        when(funcionarioRepository.save(any())).thenReturn(funcionario);

        passwordEvent.getResetPassword().setConfirmPassword("321");

        var exception = assertThrows(
                PasswordNotEqualsException.class,
                () -> funcionarioService.resetPassword(passwordEvent)
        );

        assertEquals("As senhas não coincidem", exception.getMessage());
    }

    @Test
    void whenResetPasswordThenThrowFuncionarioNotFoundException() {
        doNothing().when(eventPublisher).publishEvent(passwordEvent);

        when(funcionarioRepository.findBySubId(any(UUID.class))).thenReturn(Optional.empty());
        when(funcionarioRepository.save(any())).thenReturn(funcionario);

        var exception = assertThrows(
                FuncionarioNotFoundException.class,
                () -> funcionarioService.resetPassword(passwordEvent)
        );

        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    private void mockData() {
        funcionario = new Funcionario(
                ID,
                NOME,
                CPF,
                EMAIL,
                SENHA,
                DATA_NASCIMENTO,
                SETOR,
                ID,
                List.of()
        );
        funcionarioDTO = new FuncionarioDTO(
                ID,
                NOME,
                EMAIL,
                DATA_NASCIMENTO,
                SETOR
        );
        newFuncionarioDTO = new NewFuncionarioDTO(
                NOME,
                CPF,
                EMAIL,
                SENHA,
                DATA_NASCIMENTO,
                SETOR
        );
        optional = Optional.of(funcionario);
        passwordEvent = new ResetedPasswordEvent(UUID.randomUUID(), new ResetPasswordRequest("123", "123"));
        emailEvent = new SendEmailEvent(optional.get().getSubId(), mapper.map(funcionario, FuncionarioDTO.class));
    }

}