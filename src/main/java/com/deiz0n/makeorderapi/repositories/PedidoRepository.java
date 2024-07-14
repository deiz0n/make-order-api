package com.deiz0n.makeorderapi.repositories;

import com.deiz0n.makeorderapi.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    @Query("from tb_pedido p left join fetch tb_itens_pedido ip on ip.pedido.id = p.id " +
            "left join fetch tb_funcionario f on p.funcionario.id = f.id " +
            "left join fetch tb_comanda c on p.comanda.id = c.id " +
            "left join fetch tb_mesa m on p.mesa.id = m.id")
    List<Pedido> findAll();

    @Query("SELECT " +
                "I.item AS ITEM, " +
                "COUNT(P.id) AS QNTD_VENDAS " +
            "FROM tb_pedido P " +
            "JOIN FETCH tb_itens_pedido I " +
            "ON P.id = I.pedido.id " +
            "GROUP BY I.item " +
            "ORDER BY QNTD_VENDAS DESC " +
            "LIMIT 3")
    List<Object> getTopItens();

    @Query("SELECT " +
                "(F.id," +
                "F.nome, " +
                "F.email, " +
                "F.dataNascimento, " +
                "F.setor) AS FUNCIONARIO, " +
                "COUNT(P.funcionario.id) AS VENDAS " +
            "FROM tb_pedido P " +
            "JOIN FETCH tb_funcionario F " +
            "ON P.funcionario.id = F.id " +
            "GROUP BY F.id " +
            "ORDER BY VENDAS DESC " +
            "LIMIT 3")
    List<Object> getTopFuncionaios();
}
