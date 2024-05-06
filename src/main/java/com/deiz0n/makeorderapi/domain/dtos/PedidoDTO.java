package com.deiz0n.makeorderapi.domain.dtos;

import com.deiz0n.makeorderapi.domain.enums.FormaPagamento;
import com.deiz0n.makeorderapi.domain.enums.StatusPedido;
import com.deiz0n.makeorderapi.domain.entities.Comanda;
import com.deiz0n.makeorderapi.domain.entities.ItensPedido;
import com.deiz0n.makeorderapi.domain.entities.Mesa;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private UUID id;
    private Instant data;
    private FormaPagamento formaPagamento;
    private StatusPedido status;
    private Integer codigo;
    private String observacoes;
    private List<ItensPedido> itens;
    private Comanda comanda;
    private FuncionarioDTO funcionario;
    private Mesa mesa;

    @JsonProperty(value = "valor_total")
    public Double getValorTotal() {
        var soma = 0.0;
        for (ItensPedido iten : itens) {
            if (iten.getItem().getPreco() == null) break;
            soma += iten.getQuantidade() * iten.getItem().getPreco().doubleValue();
        }
       return soma;
    }

}