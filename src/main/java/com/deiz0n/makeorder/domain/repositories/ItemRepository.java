package com.deiz0n.makeorder.domain.repositories;

import com.deiz0n.makeorder.domain.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
}
