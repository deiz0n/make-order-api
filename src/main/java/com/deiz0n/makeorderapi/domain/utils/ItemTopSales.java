package com.deiz0n.makeorderapi.domain.utils;

import com.deiz0n.makeorderapi.domain.entities.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemTopSales {

    private Item item;
    private Integer vendas;

}
