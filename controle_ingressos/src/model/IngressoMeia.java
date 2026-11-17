package model;

import exception.CupomInvalidoException;

public class IngressoMeia extends Ingresso {

    private int numeroCarteirinha;

    public IngressoMeia(int codigo, String nomeComprador, double valorBase, int numeroCarteirinha) {
        super(codigo, nomeComprador, valorBase);
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
        return super.toString();
    }

    @Override
    public double aplicaCupom(String codigoCupom) throws CupomInvalidoException {
        throw new CupomInvalidoException("Cupons promocionais não são cumulativos para ingressos com meia-entrada");
    }
}
