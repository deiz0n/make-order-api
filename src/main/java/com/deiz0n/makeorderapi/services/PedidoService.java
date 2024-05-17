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
import org.springframework.beans.FatalBeanException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private PedidoRepository pedidoRepository;
    private ItensPedidoRepository itensPedidoRepository;
    private ModelMapper mapper;
    private static final Integer MIN = 1;
    private static final Integer MAX = 1000;

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


//    public PedidoDTO update(UUID id, Pedido newData) {
//        try {
//        var pedido = pedidoRepository.getReferenceById(id);
//        BeanUtils.copyProperties(newData, pedido, "id", "codigo", "data");
//
//        pedidoRepository.save(pedido);
//
//        return mapper.map(pedido, PedidoDTO.class);
//        } catch (FatalBeanException e) {
//            throw new PedidoNotFoundException("Não foi possível encontrar um pedido com o Id informado");
//        }
//    }

    public PedidoDTO updateStatus(UUID id, Pedido newStatus) {
        var pedido = pedidoRepository.getReferenceById(id);
        pedido.setStatus(newStatus.getStatus());
        pedidoRepository.save(pedido);
        return mapper.map(pedido, PedidoDTO.class);
    }
}
