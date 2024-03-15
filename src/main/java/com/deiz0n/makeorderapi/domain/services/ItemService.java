package com.deiz0n.makeorderapi.domain.services;

import com.deiz0n.makeorderapi.domain.dto.ItemDTO;
import com.deiz0n.makeorderapi.domain.models.Item;
import com.deiz0n.makeorderapi.domain.repositories.ItemRepository;
import com.deiz0n.makeorderapi.domain.services.exceptions.ResourceNotFoundException;
import com.deiz0n.makeorderapi.domain.utils.MokTopItens;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemService implements ServiceCRUD<ItemDTO, Item> {

    private ItemRepository repository;
    private ModelMapper mapper;

    public ItemService(ItemRepository itemRepository, ModelMapper mapper) {
        this.repository = itemRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ItemDTO> getResources() {
        List<ItemDTO> itens = repository.findAll()
                .stream()
                .map(x -> mapper.map(x, ItemDTO.class))
                .toList();
        return itens;
    }

    public List<MokTopItens> getTopItens() {
        return List.of(
                new MokTopItens("Massa Carbonara", 31),
                new MokTopItens("Frango à Parmegiana", 28),
                new MokTopItens("Frango à Parmegiana", 15)
        );
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
