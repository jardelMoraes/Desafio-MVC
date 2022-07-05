package com.desafio.mvc.entities;

public enum PerfilTipo {
    ADIMIN(1, "ADIMIN"), COMUM(2, "COMUM");

    private Integer codigo;
    private String descricao;

    private PerfilTipo(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}
