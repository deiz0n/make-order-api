package com.deiz0n.makeorderapi.domain.services;

import com.deiz0n.makeorderapi.domain.dto.ItemDTO;
import com.deiz0n.makeorderapi.domain.models.Item;
import com.deiz0n.makeorderapi.domain.repositories.ItemRepository;
import com.deiz0n.makeorderapi.domain.repositories.PedidoRepository;
import com.deiz0n.makeorderapi.domain.services.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

@Service
public class ItemService implements ServiceCRUD<ItemDTO, Item> {

    private ItemRepository repository;
    private ModelMapper mapper;
    private PedidoRepository pedidoRepository;

    public ItemService(ItemRepository itemRepository, ModelMapper mapper, PedidoRepository pedidoRepository) {
        this.repository = itemRepository;
        this.mapper = mapper;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public List<ItemDTO> getResources() {
        List<ItemDTO> itens = repository.findAll()
                .stream()
                .map(x -> mapper.map(x, ItemDTO.class))
                .toList();
        return itens;
    }

    public String getTopItens() {
        var itensPedido = new Hashtable<UUID, Item>();
        for (var pedido : pedidoRepository.findAll()) {
            for (var item : pedido.getItens()) {
                if (itensPedido.containsKey(item.getId())) {
                    var newItem = itensPedido.get(item.getId());
                    newItem.setQuantidade(newItem.getQuantidade() + item.getQuantidade());
                    itensPedido.put(item.getId(), newItem);
                } else {
                    itensPedido.put(item.getId(), item);
                }
            }
        }
        return itensPedido.toString();
    }

    @Override
    public Item createResource(ItemDTO newItemRequest) {
        var item = mapper.map(newItemRequest, Item.class);
        return repository.save(item);
    }

    @Override
    public Item updateResource(ItemDTO newItemRequest, UUID id) {
        var item = getByID(id);
        BeanUtils.copyProperties(newItemRequest, item, "id");
        return repository.save(item);
    }

    @Override
    public void deleteResource(UUID id) {
        var item = getByID(id);
        repository.delete(item);
    }

    public Item getByID(UUID id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Item não encontrado"));
    }
}
