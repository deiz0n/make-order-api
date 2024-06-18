package com.deiz0n.makeorderapi.domain.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Mensagem {

    private String destinatario;
    private String assunto;
    private String corpo;

}
