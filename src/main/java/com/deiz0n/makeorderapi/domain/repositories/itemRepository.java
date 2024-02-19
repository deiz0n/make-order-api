package com.deiz0n.makeorderapi.domain.repositories;

import com.deiz0n.makeorderapi.domain.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface itemRepository extends JpaRepository<Item, UUID> {
}
