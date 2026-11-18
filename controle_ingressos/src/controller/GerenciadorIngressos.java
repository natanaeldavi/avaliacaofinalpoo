package controller;

import exception.IngressoInvalidoException;
import model.Ingresso;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorIngressos {

    public List<Ingresso> listaIngressos = new ArrayList<>();

    public boolean cadastrarIngresso(Ingresso i) {
        if (listaIngressos.contains(i)) {
            return false;
        }
        listaIngressos.add(i);
        return true;
    }

    public Ingresso buscaIngresso(int codigo) throws IngressoInvalidoException {
       return listaIngressos.stream().filter(i -> i.getCodigo() == codigo).findFirst().orElseThrow(() -> new IngressoInvalidoException("Ingresso inválido/inexistente"));
    }

    public void processarValidacao(int codigo) {
        Ingresso i = buscaIngresso(codigo);
        i.validarIngresso();
    }

    public void solicitarCancelamento(int codigo) {
        Ingresso i = buscaIngresso(codigo);
        i.cancelarIngresso();
    }

    public void validarCupom(int codigo, String cupom) {
        Ingresso i = buscaIngresso(codigo);
        double valorComDesconto = i.aplicaCupom(cupom);
    }

}
