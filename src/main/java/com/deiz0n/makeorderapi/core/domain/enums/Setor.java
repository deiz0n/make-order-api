package com.deiz0n.makeorderapi.core.domain.enums;

public enum Setor {

    GARCOM("garcom"),
    COZINHA("cozinha"),
    ADMINISTRACAO("administracao");

    private String cargo;

    Setor(String cargo) {
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }
}
