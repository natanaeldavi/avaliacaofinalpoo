package model;

public enum StatusIngresso {
    DISPONIVEL("Esse ingresso está disponível"),
    UTILIZADO("Esse ingresso ja foi utilizado"),
    CANCELADO("Esse ingresso foi cancelado");

    private String descricao;

    StatusIngresso(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
