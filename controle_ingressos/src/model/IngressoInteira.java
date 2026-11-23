package model;

import exception.CupomInvalidoException;
import exception.SessaoInvalidaException;
import interfaces.Promocional;

public class IngressoInteira extends Ingresso {

    public IngressoInteira(SessaoCinema sessao, String nomeComprador) throws SessaoInvalidaException {
        super(sessao, nomeComprador);
    }

    @Override
    public double calcularValorFinal() {
        if (possuiCupom == true) {
            return valorBase - (valorBase * percentualCupom);
        } else {
            return valorBase;
        }
    }

    @Override
    public String toString() {
        return super.toString() + " | Tipo: Inteira";
    }
    @Override
    public boolean aplicaCupom(String codigoCupom) throws CupomInvalidoException {
        if (Promocional.verificaCupom(codigoCupom)) {
            if (status == StatusIngresso.DISPONIVEL) {
                if (possuiCupom == true) {
                    if (percentualCupom < Promocional.cuponsValidos.get(codigoCupom.trim().toUpperCase()) / 100) {
                        percentualCupom = Promocional.cuponsValidos.get(codigoCupom.trim().toUpperCase()) / 100;
                        return true;
                    } else {
                        throw new CupomInvalidoException("Este ingresso já possui um cupom aplicado");
                    }
                } else {
                    possuiCupom = true;
                    percentualCupom = Promocional.cuponsValidos.get(codigoCupom.trim().toUpperCase()) / 100;
                    return true;
                }
            } else {
                throw new CupomInvalidoException("Não é mais possível aplicar cupom de desconto nesse ingresso");
            }
        } else {
            throw new CupomInvalidoException("Este cupom é inválido/inexistente");
        }
    }
}
