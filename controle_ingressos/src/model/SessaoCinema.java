package model;

import java.time.LocalDateTime;

public class SessaoCinema {

    protected int idSessao;
    protected LocalDateTime dataHoraSessao;
    protected Filme filmeSessao;
    protected int vagasSessao;
    protected boolean isDisponivel;
    protected String salaSessao;
    private static int contadorIdSessao = 1;

    public SessaoCinema(LocalDateTime dataHoraSessao, Filme filmeSessao, int vagasSessao, boolean isDisponivel, String salaSessao) {
        idSessao = contadorIdSessao;
        contadorIdSessao++;
        this.dataHoraSessao = dataHoraSessao;
        this.filmeSessao = filmeSessao;
        this.vagasSessao = vagasSessao;
        this.isDisponivel = isDisponivel;
        this.salaSessao = salaSessao;
    }

    public int getIdSessao() {
        return idSessao;
    }

    public LocalDateTime getDataHoraSessao() {
        return dataHoraSessao;
    }

    public void setDataHoraSessao(LocalDateTime dataHoraSessao) {
        this.dataHoraSessao = dataHoraSessao;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof SessaoCinema sessao)) return false;
        return this.idSessao == sessao.getIdSessao();
    }

    @Override
    public int hashCode() {
        return idSessao;
    }

    @Override
    public String toString() {
        return "Id da Sessão: " + idSessao + " | Data e Hora: " + dataHoraSessao + " | Sala: " + salaSessao +
                " | Filme: " + filmeSessao.getNomeFilme() + " | Nota: " + filmeSessao.getNotaMedia() + " | Classificacao: " + filmeSessao.getClassificacaoEtaria();
    }
}
