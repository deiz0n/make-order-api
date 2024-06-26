package com.deiz0n.makeorderapi.repositories;

import com.deiz0n.makeorderapi.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    @Query("SELECT I.item AS ITEM, COUNT(P.id) AS QNTD_VENDAS " +
            "FROM tb_pedido P " +
            "INNER JOIN tb_itens_pedido I " +
            "ON P.id = I.pedido.id " +
            "GROUP BY I.item " +
            "ORDER BY QNTD_VENDAS DESC " +
            "LIMIT 3")
    List<Object> getTopItens();

}
