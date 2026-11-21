package model;

import java.util.Objects;

public class Filme {

    protected int idFilme;
    private static int contatorIdFilme = 1;
    protected String nomeFilme;
    protected String classificacaoEtaria;
    protected String descricaoFilme;
    protected double notaMedia;
    protected double valorIngresso;

    public Filme(String nomeFilme, String classificacaoEtaria, String descricaoFilme, double notaMedia, double valorIngresso) {
        idFilme = contatorIdFilme;
        contatorIdFilme++;
        this.nomeFilme = nomeFilme;
        this.classificacaoEtaria = classificacaoEtaria;
        this.descricaoFilme = descricaoFilme;
        this.notaMedia = notaMedia;
        this.valorIngresso = valorIngresso;
    }

    public int getIdFilme() {
        return idFilme;
    }

    public String getNomeFilme() {
        return nomeFilme;
    }

    public void setNomeFilme(String nomeFilme) {
        this.nomeFilme = nomeFilme;
    }

    public String getClassificacaoEtaria() {
        return classificacaoEtaria;
    }

    public double getValorIngresso() {
        return valorIngresso;
    }

    public void setClassificacaoEtaria(String classificacaoEtaria) {
        this.classificacaoEtaria = classificacaoEtaria;
    }

    public String getDescricaoFilme() {
        return descricaoFilme;
    }

    public void setDescricaoFilme(String descricaoFilme) {
        this.descricaoFilme = descricaoFilme;
    }

    public double getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(double notaMedia) {
        this.notaMedia = notaMedia;
    }

    public boolean equals(Object o) {
        if(!(o instanceof Filme filme)) return false;
        return this.idFilme == filme.getIdFilme();
    }

    @Override
    public int hashCode() {
        return idFilme;
    }

    @Override
    public String toString() {
        return "Filme: " + nomeFilme + " | Nota: " + notaMedia + " | Classificacao: " + classificacaoEtaria;
    }
}
