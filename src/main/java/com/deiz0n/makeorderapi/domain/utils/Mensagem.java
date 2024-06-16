package com.deiz0n.makeorderapi.domain.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Mensagem {

    private Set<String> destinatario;
    private String assunto;
    private String corpo;

}
