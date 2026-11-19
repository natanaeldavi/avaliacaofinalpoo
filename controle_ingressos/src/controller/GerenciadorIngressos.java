package controller;

import exception.CupomInvalidoException;
import exception.IngressoInvalidoException;
import model.Ingresso;
import model.StatusIngresso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void processarValidacao(int codigo) throws IngressoInvalidoException {
        Ingresso i = buscaIngresso(codigo);
        i.validarIngresso();
    }

    public void solicitarCancelamento(int codigo) throws IngressoInvalidoException {
        Ingresso i = buscaIngresso(codigo);
        i.cancelarIngresso();
    }

    public boolean validarCupom(int codigo, String cupom) throws IngressoInvalidoException, CupomInvalidoException {
        Ingresso i = buscaIngresso(codigo);
        return i.aplicaCupom(cupom);
    }

    public double calcularFaturamentoTotal() {
        return listaIngressos.stream().filter(i -> i.getStatus() != StatusIngresso.CANCELADO).mapToDouble(Ingresso::calcularValorFinal).sum();
    }

    public String listarIngressos() {
        if (listaIngressos.isEmpty()) {
            return "Nenhum ingresso cadastrado";
        } else {
            return listaIngressos.stream().map(Ingresso::toString).collect(Collectors.joining("\n"));
        }
    }

}
