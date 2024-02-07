package com.deiz0n.makeorder.api.controllers;

import com.deiz0n.makeorder.domain.dtos.PedidoDTO;
import com.deiz0n.makeorder.domain.models.Comanda;
import com.deiz0n.makeorder.domain.models.Funcionario;
import com.deiz0n.makeorder.domain.models.Mesa;
import com.deiz0n.makeorder.domain.models.Pedido;
import com.deiz0n.makeorder.domain.models.enums.FormaPagamento;
import com.deiz0n.makeorder.domain.models.enums.StatusPedido;
import com.deiz0n.makeorder.domain.services.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PedidoControllerTest {

    public static final Instant DATA = Instant.now();
    public static final UUID ID = UUID.randomUUID();
    public static final FormaPagamento FORMA_PAGAMENTO = FormaPagamento.DEBITO;
    public static final StatusPedido STATUS_PEDIDO = StatusPedido.CONCLUIDO;
    public static final Comanda COMANDA = new Comanda();
    public static final Mesa MESA = new Mesa();
    public static final Funcionario FUNCIONARIO = new Funcionario();
    public static final int INDEX = 0;

    @InjectMocks
    private PedidoController controller;
    @Mock
    private PedidoService service;

    private Pedido pedido;
    private PedidoDTO pedidoDTO;
    private Optional<Pedido> optional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPedido();
    }

    @Test
    void whenGetPedidosThenReturnOk() {
        when(service.getResources()).thenReturn(List.of(pedidoDTO));

        ResponseEntity<List<PedidoDTO>> response = controller.getPedidos();

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(PedidoDTO.class, response.getBody().get(INDEX).getClass());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(DATA, response.getBody().get(INDEX).getData());
        assertEquals(FORMA_PAGAMENTO, response.getBody().get(INDEX).getFormaPagamento());
        assertEquals(STATUS_PEDIDO, response.getBody().get(INDEX).getStatusPedido());
    }

    @Test
    void whenCreatePedidoThenReturnCreated() {
        when(service.createResource(any())).thenReturn(pedido);

        ResponseEntity<PedidoDTO> response = controller.createPedido(pedidoDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getHeaders().get("Location"));

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(PedidoDTO.class, response.getBody().getClass());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertEquals(ID, response.getBody().getId());
        assertEquals(FORMA_PAGAMENTO, response.getBody().getFormaPagamento());
        assertEquals(STATUS_PEDIDO, response.getBody().getStatusPedido());
    }

    @Test
    void whenUpdateStatusThenReturnOk() {
        when(service.findByID(any(UUID.class))).thenReturn(pedido);
        when(service.updateStatus(any(UUID.class), any())).thenReturn(pedidoDTO);

        ResponseEntity<PedidoDTO> response = controller.updateStatus(ID, pedidoDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(PedidoDTO.class, response.getBody().getClass());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(STATUS_PEDIDO, response.getBody().getStatusPedido());
    }

    @Test
    void whenDeleteResourceThenReturnNoContent() {
        when(service.findByID(any(UUID.class))).thenReturn(pedido);
        doNothing().when(service).deleteResource(any(UUID.class));

        ResponseEntity<?> response = controller.deletePedido(ID);

        assertNotNull(response);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(service, times(1)).deleteResource(ID);
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