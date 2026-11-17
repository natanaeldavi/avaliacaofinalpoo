package model;

import exception.CupomInvalidoException;
import interfaces.Promocional;

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

    @Override
    public double aplicaCupom(String codigoCupom) throws CupomInvalidoException {
        if(Promocional.verificaCupom(codigoCupom)) {
            return calcularValorFinal() - (calcularValorFinal() * (Promocional.cuponsValidos.get(codigoCupom.trim().toUpperCase()) / 100));
        } else {
            throw new CupomInvalidoException("Ingresso inválido/inexistente");
        }
    }
}
