package com.deiz0n.makeorderapi.adapters.services;

import com.deiz0n.makeorderapi.core.domain.dtos.PedidoDTO;
import com.deiz0n.makeorderapi.core.domain.enums.StatusPedido;
import com.deiz0n.makeorderapi.core.exceptions.PedidoNotFoundException;
import com.deiz0n.makeorderapi.infrastructure.config.ModelMapperConfig;
import com.deiz0n.makeorderapi.infrastructure.entities.ItensPedido;
import com.deiz0n.makeorderapi.infrastructure.entities.Pedido;
import com.deiz0n.makeorderapi.infrastructure.persistence.impl.ItensPedidoRepositoryImpl;
import com.deiz0n.makeorderapi.infrastructure.persistence.impl.PedidoRepositoryImpl;
import com.deiz0n.makeorderapi.usecases.pedido.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoService implements CreatePedidoUseCase, DeletePedidoUseCase, GetPedidosUseCase,
        GetPedidoUseCase, UpdatePedidoUseCase, UpdateStatusPedidoUseCase {

    private PedidoRepositoryImpl pedidoRepository;
    private ItensPedidoRepositoryImpl itensPedidoRepository;
    private ModelMapperConfig mapperConfig;

    public PedidoService(PedidoRepositoryImpl pedidoRepository, ModelMapperConfig mapperConfig, ItensPedidoRepositoryImpl itensPedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.mapperConfig = mapperConfig;
        this.itensPedidoRepository = itensPedidoRepository;
    }

    @Override
    public PedidoDTO createPedido(Pedido newPedidoRequest) {
        newPedidoRequest.setData(Instant.now());
        newPedidoRequest.setStatus(StatusPedido.PENDENTE);

        var pedido = pedidoRepository.createPedido(newPedidoRequest);

        for (int i = 0; i < pedido.getItens().size(); i++) {
            itensPedidoRepository.createItensPedido(
                    new ItensPedido(
                            UUID.randomUUID(),
                            pedido.getItens().get(i).getQuantidade(),
                            pedido.getItens().get(i).getItem(),
                            pedido));
        }

        return mapperConfig.modelMapper().map(pedido, PedidoDTO.class);
    }

    @Override
    public void deletePedodo(UUID id) {
        pedidoRepository.deletePedido(id);
    }

    @Override
    public PedidoDTO getPedido(UUID id) {
        return pedidoRepository.getPedido(id)
                .map(pedido -> mapperConfig.modelMapper().map(pedido, PedidoDTO.class))
                .orElseThrow(() -> new PedidoNotFoundException("Pedido não encontrado"));
    }

    @Override
    public List<PedidoDTO> getPedidos() {
        return pedidoRepository.getPedidos()
                .stream()
                .map(pedido -> mapperConfig.modelMapper().map(pedido, PedidoDTO.class))
                .toList();
    }

    @Override
    public PedidoDTO updatePedido(UUID id, Pedido newDataRequest) {
        var oldDataRequest = pedidoRepository.updatePedido(id, newDataRequest);
        return mapperConfig.modelMapper().map(oldDataRequest, PedidoDTO.class);
    }

    @Override
    public PedidoDTO updateStatus(UUID id, Pedido newStatusRequest) {
        var oldStatus = pedidoRepository.updateStatus(id, newStatusRequest);
        return mapperConfig.modelMapper().map(oldStatus, PedidoDTO.class);
    }
}
