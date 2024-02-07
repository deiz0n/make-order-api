package com.deiz0n.makeorder.domain.services;

import com.deiz0n.makeorder.domain.dtos.PedidoDTO;
import com.deiz0n.makeorder.domain.models.Comanda;
import com.deiz0n.makeorder.domain.models.Funcionario;
import com.deiz0n.makeorder.domain.models.Mesa;
import com.deiz0n.makeorder.domain.models.Pedido;
import com.deiz0n.makeorder.domain.models.enums.FormaPagamento;
import com.deiz0n.makeorder.domain.models.enums.StatusPedido;
import com.deiz0n.makeorder.domain.repositories.PedidoRepository;
import com.deiz0n.makeorder.domain.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PedidoServiceTest {

    public static final Instant DATA = Instant.now();
    public static final UUID ID = UUID.randomUUID();
    public static final FormaPagamento FORMA_PAGAMENTO = FormaPagamento.DEBITO;
    public static final StatusPedido STATUS_PEDIDO = StatusPedido.CONCLUIDO;
    public static final Comanda COMANDA = new Comanda();
    public static final Mesa MESA = new Mesa();
    public static final Funcionario FUNCIONARIO = new Funcionario();
    public static final int INDEX = 0;

    @InjectMocks
    private PedidoService service;
    @Mock
    private PedidoRepository repository;
    @Mock
    private ModelMapper mapper;


    private Pedido pedido;
    private PedidoDTO pedidoDTO;
    private Optional<Pedido> optional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPedido();
    }

    @Test
    void whenGetResourcesThenReturnListOfPedidoDTO() {
        when(repository.findAll()).thenReturn(List.of(pedido));
        when(mapper.map(any(), any())).thenReturn(pedidoDTO);

        List<PedidoDTO> response = service.getResources();

        assertNotNull(response);
        assertEquals(PedidoDTO.class, response.get(INDEX).getClass());
        assertEquals(1, response.size());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(DATA, response.get(INDEX).getData());
        assertEquals(FORMA_PAGAMENTO, response.get(INDEX).getFormaPagamento());
        assertEquals(STATUS_PEDIDO, response.get(INDEX).getStatusPedido());
    }

    @Test
    void whenCreateResourceThenReturnPedido() {
        when(repository.save(any())).thenReturn(pedido);
        when(mapper.map(any(), any())).thenReturn(pedido);

        Pedido response = service.createResource(pedidoDTO);

        assertNotNull(response);
        assertEquals(Pedido.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(DATA, response.getData());
        assertEquals(FORMA_PAGAMENTO, response.getFormaPagamento());
        assertEquals(STATUS_PEDIDO, response.getStatusPedido());
    }

    @Test
    void whenDeleteResourceThenReturnVoid() {
        when(repository.findById(any(UUID.class))).thenReturn(optional);

        doNothing().when(repository).delete(pedido);

        service.deleteResource(ID);

        verify(repository, times(1)).delete(pedido);
    }

    @Test
    void whenDeleteResourceThenReturnResourceNotFoundException() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        try {
            service.deleteResource(ID);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals(String.format("O pedido com id: %s não foi encontrado", ID.toString()), e.getMessage());
        }
    }

    @Test
    void whenUpdateStatusThenReturnPedidoDTO() {
        when(repository.save(any())).thenReturn(pedido);
        when(repository.findById(any(UUID.class))).thenReturn(optional);

        PedidoDTO response = service.updateStatus(ID, pedidoDTO);

        assertNotNull(response);
        assertEquals(PedidoDTO.class, response.getClass());
        assertEquals(STATUS_PEDIDO, response.getStatusPedido());
    }

    @Test
    void whenUpdateStatusThenReturnResourceNotFoundException() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        try {
            PedidoDTO response = service.updateStatus(ID, pedidoDTO);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals(String.format("O pedido com id: %s não foi encontrado", ID.toString()), e.getMessage());
        }
    }

    private void startPedido() {
        pedido = new Pedido(ID
                ,DATA
                ,FORMA_PAGAMENTO
                ,STATUS_PEDIDO
                ,COMANDA
                ,MESA
                ,FUNCIONARIO);
        pedidoDTO = new PedidoDTO(ID
                ,DATA
                ,FORMA_PAGAMENTO
                ,STATUS_PEDIDO);
        optional = Optional.of(new Pedido(ID
                ,DATA
                ,FORMA_PAGAMENTO
                ,STATUS_PEDIDO
                ,COMANDA
                ,MESA
                ,FUNCIONARIO));
    }


}