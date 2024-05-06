package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.dtos.PedidoDTO;
import com.deiz0n.makeorderapi.domain.entities.ItensPedido;
import com.deiz0n.makeorderapi.domain.entities.Pedido;
import com.deiz0n.makeorderapi.domain.enums.StatusPedido;
import com.deiz0n.makeorderapi.domain.exceptions.PedidoNotFoundException;
import com.deiz0n.makeorderapi.repositories.ItensPedidoRepository;
import com.deiz0n.makeorderapi.repositories.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItensPedidoRepository itensPedidoRepository;
    private final ModelMapper mapper;
    private final Integer MIN = 1;
    private final Integer MAX = 1000;

    public PedidoService(PedidoRepository pedidoRepository, ItensPedidoRepository itensPedidoRepository, ModelMapper mapper) {
        this.pedidoRepository = pedidoRepository;
        this.itensPedidoRepository = itensPedidoRepository;
        this.mapper = mapper;
    }

    public List<PedidoDTO> getAll() {
        return pedidoRepository.findAll()
                .stream()
                .map(pedidos -> mapper.map(pedidos, PedidoDTO.class))
                .collect(Collectors.toList());
    }

    public PedidoDTO getById(UUID id) {
        return pedidoRepository.findById(id)
                .map(pedido -> mapper.map(pedido, PedidoDTO.class))
                .orElseThrow(() -> new PedidoNotFoundException("Não foi possível encontrar um pedido com o Id informado"));
    }

    public PedidoDTO create(Pedido newPedido) {
        newPedido.setCodigo((int) (Math.random() * MAX) + MIN);
        newPedido.setData(Instant.now());
        newPedido.setStatus(StatusPedido.PENDENTE);

        var pedido = pedidoRepository.save(newPedido);

//        for (int i = 0; i < pedido.getItens().size(); i++) {
//            itensPedidoRepository.save(new ItensPedido(
//                    UUID.randomUUID(),
//                    pedido.getItens().get(i).getQuantidade(),
//                    pedido.getItens().get(i).getItem(),
//                    pedido
//            ));
//        }

        for (ItensPedido itens : pedido.getItens()) {
            itensPedidoRepository.save(new ItensPedido(
                    UUID.randomUUID(),
                    itens.getQuantidade(),
                    itens.getItem(),
                    pedido
            ));
        }

        return mapper.map(pedido, PedidoDTO.class);
    }

    public void delete(UUID id) {
        var pedido = getById(id);
        pedidoRepository.deleteById(pedido.getId());
    }

    public PedidoDTO update(UUID id, Pedido newData) {
        var pedido = getById(id);
        BeanUtils.copyProperties(newData, pedido, "id", "codigo");
        pedidoRepository.save(mapper.map(pedido, Pedido.class));
        return pedido;
    }

    public PedidoDTO updateStatus(UUID id, StatusPedido newStatus) {
        var pedido = getById(id);
        pedido.setStatus(newStatus);
        pedidoRepository.save(mapper.map(pedido, Pedido.class));
        return pedido;
    }
}
