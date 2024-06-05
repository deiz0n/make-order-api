package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.dtos.FuncionarioDTO;
import com.deiz0n.makeorderapi.domain.dtos.ItemDTO;
import com.deiz0n.makeorderapi.domain.dtos.PedidoDTO;
import com.deiz0n.makeorderapi.domain.entities.*;
import com.deiz0n.makeorderapi.domain.enums.FormaPagamento;
import com.deiz0n.makeorderapi.domain.enums.StatusPedido;
import com.deiz0n.makeorderapi.domain.exceptions.PedidoNotFoundException;
import com.deiz0n.makeorderapi.domain.exceptions.ResourceNotFoundException;
import com.deiz0n.makeorderapi.repositories.ItensPedidoRepository;
import com.deiz0n.makeorderapi.repositories.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PedidoServiceTest {

    public static final Integer INDEX = 0;
    public static final UUID ID = UUID.randomUUID();
    public static final Instant INSTANT = Instant.now();
    public static final FormaPagamento FORMA_PAGAMENTO = FormaPagamento.CREDITO;
    public static final StatusPedido STATUS_PEDIDO = StatusPedido.PENDENTE;
    public static final Integer CODIGO = 12;
    public static final String OBSERVACOES = "Obs 1";
    public static final Comanda COMANDA = new Comanda();
    public static final Funcionario FUNCIONARIO = new Funcionario();
    public static final Mesa MESA = new Mesa();
    public static final FuncionarioDTO FUNCIONARIO_DTO = new FuncionarioDTO();

    @InjectMocks
    private PedidoService service;
    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private ItensPedidoRepository itensPedidoRepository;
    @Mock
    private ModelMapper mapper;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    private Pedido pedido;
    private PedidoDTO pedidoDTO;
    private Optional<Pedido> optional;
    @Autowired
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockData();
    }

    @Test
    void whenGetAllThenReturnListOfPedidoDTO() {
        when(pedidoRepository.findAll()).thenReturn(List.of(pedido));
        when(mapper.map(any(), any())).thenReturn(pedidoDTO);

        List<PedidoDTO> responseList = service.getAll();

        assertNotNull(responseList);
        assertEquals(1, responseList.size());
        assertEquals(ArrayList.class, responseList.getClass());
        assertEquals(PedidoDTO.class, responseList.get(INDEX).getClass());

        assertEquals(ID, responseList.get(INDEX).getId());
        assertEquals(INSTANT, responseList.get(INDEX).getData());
        assertEquals(FORMA_PAGAMENTO, responseList.get(INDEX).getFormaPagamento());
        assertEquals(STATUS_PEDIDO, responseList.get(INDEX).getStatus());
        assertEquals(CODIGO, responseList.get(INDEX).getCodigo());
        assertEquals(OBSERVACOES, responseList.get(INDEX).getObservacoes());
        assertEquals(COMANDA, responseList.get(INDEX).getComanda());
        assertEquals(FUNCIONARIO_DTO, responseList.get(INDEX).getFuncionario());
        assertEquals(MESA, responseList.get(INDEX).getMesa());
    }

    @Test
    void whenGetByIdThenReturnPedidoDTO() {
        when(pedidoRepository.findById(any(UUID.class))).thenReturn(optional);
        when(mapper.map(any(), any())).thenReturn(pedidoDTO);

        PedidoDTO response = service.getById(ID);

        assertNotNull(response);
        assertEquals(PedidoDTO.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(INSTANT, response.getData());
        assertEquals(FORMA_PAGAMENTO, response.getFormaPagamento());
        assertEquals(STATUS_PEDIDO, response.getStatus());
        assertEquals(CODIGO, response.getCodigo());
        assertEquals(OBSERVACOES, response.getObservacoes());
        assertEquals(COMANDA, response.getComanda());
        assertEquals(FUNCIONARIO_DTO, response.getFuncionario());
        assertEquals(MESA, response.getMesa());
    }

    @Test
    void whenGetByIdThenThrowPedidoNotFoundException() {
        when(pedidoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        when(mapper.map(any(), any())).thenReturn(pedidoDTO);

        var exception = assertThrows(
                PedidoNotFoundException.class,
                () -> pedidoService.getById(ID)
        );

        assertEquals("Não foi possível encontrar um pedido com o Id informado", exception.getMessage());
    }

    @Test
    void whenCreateThenReturnPedidoDTO() {
        when(pedidoRepository.save(any())).thenReturn(pedido);
        when(itensPedidoRepository.save(any())).thenReturn(new ItensPedido());
        when(mapper.map(any(), any())).thenReturn(pedidoDTO);

        PedidoDTO response = service.create(pedido);

        assertNotNull(response);
        assertEquals(PedidoDTO.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(INSTANT, response.getData());
        assertEquals(FORMA_PAGAMENTO, response.getFormaPagamento());
        assertEquals(STATUS_PEDIDO, response.getStatus());
        assertEquals(CODIGO, response.getCodigo());
        assertEquals(OBSERVACOES, response.getObservacoes());
        assertEquals(COMANDA, response.getComanda());
        assertEquals(FUNCIONARIO_DTO, response.getFuncionario());
        assertEquals(MESA, response.getMesa());
    }

    @Test
    void whenDeleteThenDontReturn() {
        when(pedidoRepository.findById(any(UUID.class))).thenReturn(optional);
        when(mapper.map(any(), any())).thenReturn(pedidoDTO);

        doNothing().when(pedidoRepository).deleteById(any(UUID.class));

        service.delete(ID);

        verify(pedidoRepository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    void whenDeleteThenThrowPedidoNotFoundException() {
        when(pedidoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        var exception = assertThrows(
                PedidoNotFoundException.class,
                () -> pedidoService.delete(ID)
        );

        assertEquals("Não foi possível encontrar um pedido com o Id informado", exception.getMessage());
    }

    @Test
    void whenUpdateThenReturnPedidoDTO() {
        doNothing().when(eventPublisher).publishEvent(any());

        when(pedidoRepository.getReferenceById(any(UUID.class))).thenReturn(pedido);
        when(pedidoRepository.save(any())).thenReturn(pedido);
        when(mapper.map(any(), any())).thenReturn(pedidoDTO);

        PedidoDTO response = service.updateStatus(ID, pedido);

        assertNotNull(response);
        assertEquals(PedidoDTO.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(INSTANT, response.getData());
        assertEquals(FORMA_PAGAMENTO, response.getFormaPagamento());
        assertEquals(STATUS_PEDIDO, response.getStatus());
        assertEquals(CODIGO, response.getCodigo());
        assertEquals(OBSERVACOES, response.getObservacoes());
        assertEquals(COMANDA, response.getComanda());
        assertEquals(FUNCIONARIO_DTO, response.getFuncionario());
        assertEquals(MESA, response.getMesa());
    }

    @Test
    void whenUpdateThenThrowPedidoNotFoundException() {
        when(pedidoRepository.getReferenceById(any(UUID.class))).thenReturn(isNull());

        var exception = assertThrows(
                PedidoNotFoundException.class,
                () -> pedidoService.update(ID, pedido)
        );

        assertEquals("Não foi possível encontrar o pedido com o Id informado", exception.getMessage());
    }

    @Test
    void whenUpdateStatusThenReturnPedidoDTO() {
        when(pedidoRepository.getReferenceById(any(UUID.class))).thenReturn(pedido);
        when(pedidoRepository.save(any())).thenReturn(pedido);
        when(mapper.map(any(), any())).thenReturn(pedidoDTO);

        PedidoDTO response = service.updateStatus(ID, pedido);

        assertNotNull(response);
        assertEquals(PedidoDTO.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(STATUS_PEDIDO, response.getStatus());
    }

    @Test
    void whenUpdateStatusThenThrowPedidoNotFoundException() {
        when(pedidoRepository.getReferenceById(any(UUID.class))).thenReturn(null);

        var exception = assertThrows(
                PedidoNotFoundException.class,
                () -> pedidoService.updateStatus(ID, pedido)
        );

        assertEquals("Não foi possível encontrar o pedido com o Id informado", exception.getMessage());
    }

    public void mockData() {
        pedido = new Pedido(
                ID,
                INSTANT,
                FORMA_PAGAMENTO,
                STATUS_PEDIDO,
                CODIGO,
                OBSERVACOES,
                List.of(new ItensPedido()),
                COMANDA,
                FUNCIONARIO,
                MESA
        );
        pedidoDTO = new PedidoDTO(ID,
                INSTANT,
                FORMA_PAGAMENTO,
                STATUS_PEDIDO,
                CODIGO,
                OBSERVACOES,
                List.of(new ItensPedido()),
                COMANDA,
                FUNCIONARIO_DTO,
                MESA);
        optional = Optional.of(pedido);
    }
}