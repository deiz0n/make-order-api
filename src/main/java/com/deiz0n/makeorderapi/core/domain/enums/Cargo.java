package com.deiz0n.makeorderapi.core.domain.enums;

public enum Cargo {

    GARCOM("garcom"),
    COZINHEIRO("cozinheiro"),
    ADMINISTRADOR("administrador");

    private String cargo;

    Cargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }
}
