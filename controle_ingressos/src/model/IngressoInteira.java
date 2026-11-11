package model;

public class IngressoInteira extends Ingresso {

    public IngressoInteira(int codigo, String nomeComprador, double valorBase) {
        super(codigo, nomeComprador, valorBase);
    }

    @Override
    public double calcularValorFinal() {
        return valorBase;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
