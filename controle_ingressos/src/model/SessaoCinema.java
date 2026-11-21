package model;

import exception.IngressoInvalidoException;
import exception.SessaoInvalidaException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SessaoCinema {

    protected int idSessao;
    protected LocalDateTime dataHoraSessao;
    protected Filme filmeSessao;
    protected int vagasSessao;
    protected int vagasDisponiveis;
    protected List<Ingresso> listIngressosSessao = new ArrayList<>();
    protected String salaSessao;
    private static int contadorIdSessao = 1;

    public SessaoCinema(LocalDateTime dataHoraSessao, Filme filmeSessao, int vagasSessao, String salaSessao) {
        idSessao = contadorIdSessao;
        contadorIdSessao++;
        this.dataHoraSessao = dataHoraSessao;
        this.filmeSessao = filmeSessao;
        this.vagasSessao = vagasSessao;
        this.vagasDisponiveis = vagasSessao;
        this.salaSessao = salaSessao;
    }

    public int getIdSessao() {
        return idSessao;
    }

    public boolean adicionarIngressoSessao(Ingresso i) throws SessaoInvalidaException {
        if (!(vagasDisponiveis <= 0)) {
            listIngressosSessao.add(i);
            vagasDisponiveis--;
            return true;
        } else {
            throw new SessaoInvalidaException("Vagas para essa sessão esgotadas");
        }
    }

    public boolean liberarVaga(Ingresso i) throws IngressoInvalidoException {
        if (listIngressosSessao.contains(i)) {
            vagasDisponiveis++;
            listIngressosSessao.remove(i);
            return true;
        } else {
            throw new IngressoInvalidoException("Ingresso inexistente / inválido");
        }
    }

    public LocalDateTime getDataHoraSessao() {
        return dataHoraSessao;
    }

    public int getVagasDisponiveis() {
        return vagasDisponiveis;
    }

    public int getVagasSessao() {
        return vagasSessao;
    }

    public List<Ingresso> getListIngressosSessao() {
        return List.copyOf(listIngressosSessao);
    }

    public String getSalaSessao() {
        return salaSessao;
    }

    public void setDataHoraSessao(LocalDateTime dataHoraSessao) {
        this.dataHoraSessao = dataHoraSessao;
    }

    public Filme getFilmeSessao() {
        return filmeSessao;
    }

    public List<Ingresso> getIngressosSessao() {
        return List.copyOf(listIngressosSessao);
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
