package com.deiz0n.makeorder.domain.services;

import com.deiz0n.makeorder.domain.dtos.ItemDTO;
import com.deiz0n.makeorder.domain.models.Item;
import com.deiz0n.makeorder.domain.repositories.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private ItemRepository itemRepository;
    private ModelMapper mapper;

    public ItemService(ItemRepository itemRepository, ModelMapper mapper) {
        this.itemRepository = itemRepository;
        this.mapper = mapper;
    }

    public List<ItemDTO> getResouces() {
        List<ItemDTO> itens = itemRepository.findAll()
                .stream()
                .map(x -> mapper.map(x, ItemDTO.class))
                .toList();
        return itens;
    }

    public ItemDTO createResource(ItemDTO newItem) {
        var item = mapper.map(newItem, Item.class);
        itemRepository.save(item);
        return newItem;
    }
}
