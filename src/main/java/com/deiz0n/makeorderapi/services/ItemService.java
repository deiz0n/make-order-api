package com.deiz0n.makeorderapi.services;

import com.deiz0n.makeorderapi.domain.dtos.ItemDTO;
import com.deiz0n.makeorderapi.domain.entities.Item;
import com.deiz0n.makeorderapi.domain.exceptions.ItemNotFoundException;
import com.deiz0n.makeorderapi.repositories.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private ItemRepository itemRepository;
    private ModelMapper mapper;

    public ItemService(ItemRepository itemRepository, ModelMapper mapper) {
        this.itemRepository = itemRepository;
        this.mapper = mapper;
    }

    public List<ItemDTO> getAll() {
        return itemRepository.findAll()
                .stream()
                .map(item -> mapper.map(item, ItemDTO.class))
                .collect(Collectors.toList());
    }

    public ItemDTO getById(UUID id) {
        return itemRepository.findById(id)
                .map(item -> mapper.map(item, ItemDTO.class))
                .orElseThrow(() -> new ItemNotFoundException("Não foi possível encontrar um pedido com o Id informado"));
    }

    public ItemDTO create(Item newItem) {
        itemRepository.save(newItem);
        return mapper.map(newItem, ItemDTO.class);
    }

    public void delete(UUID id) {
        var item = getById(id);
        itemRepository.deleteById(item.getId());
    }

    public ItemDTO update(UUID id, Item newData) {
        try {
            var item = itemRepository.getReferenceById(id);

            BeanUtils.copyProperties(newData, item, "id", "itensPedidos", "categoria");

            itemRepository.save(item);

            return mapper.map(item, ItemDTO.class);
        } catch (FatalBeanException e) {
            throw new ItemNotFoundException("Não foi possível encontrar um pedido com o Id informado");
        }
    }
}
