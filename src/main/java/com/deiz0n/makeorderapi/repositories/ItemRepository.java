package com.deiz0n.makeorderapi.repositories;

import com.deiz0n.makeorderapi.domain.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    @Query("SELECT I.item AS item, COUNT(P.id) AS QNTD_VENDAS " +
            "FROM tb_pedido P " +
            "INNER JOIN tb_itens_pedido I " +
            "ON P.id = i.pedido.id " +
            "GROUP BY I.item " +
            "ORDER BY QNTD_VENDAS DESC " +
            "LIMIT 3")
    List<Object> getTopSales();

}
