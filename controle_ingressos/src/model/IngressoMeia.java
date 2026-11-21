package model;

import exception.CupomInvalidoException;

public class IngressoMeia extends Ingresso {

    private int numeroCarteirinha;

    public IngressoMeia(SessaoCinema sessao,String nomeComprador, int numeroCarteirinha) {
        super(sessao, nomeComprador);
        this.numeroCarteirinha = numeroCarteirinha;
    }

    @Override
    public double calcularValorFinal() {
        return (valorBase / 2);
    }

    public int getNumeroCarteirinha() {
        return numeroCarteirinha;
    }

    @Override
    public String toString() {
        return super.toString() + " | Tipo: Meia | Carteirinha: " + numeroCarteirinha;
    }

    @Override
    public boolean aplicaCupom(String codigoCupom) throws CupomInvalidoException {
        throw new CupomInvalidoException("Cupons promocionais não são cumulativos para ingressos com meia-entrada");
    }
}
