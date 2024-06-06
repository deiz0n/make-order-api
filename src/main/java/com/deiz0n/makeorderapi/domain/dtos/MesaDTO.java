package com.deiz0n.makeorderapi.domain.dtos;


import java.util.UUID;

public record MesaDTO(
        UUID id,
        Integer numero,
        String cliente
) {
}
