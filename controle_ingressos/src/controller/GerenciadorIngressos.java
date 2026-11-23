package controller;

import exception.CupomInvalidoException;
import exception.FilmeInvalidoException;
import exception.IngressoInvalidoException;
import exception.SessaoInvalidaException;
import model.Filme;
import model.Ingresso;
import model.SessaoCinema;
import model.StatusIngresso;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GerenciadorIngressos {

    public List<Ingresso> listaIngressos = new ArrayList<>();
    public List<Filme> listaFilmes = new ArrayList<>();
    public List<SessaoCinema> listaSessoes = new ArrayList<>();

    // Métodos gerenciamento Ingressos
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
        Collections.sort(listaIngressos);
        if (listaIngressos.isEmpty()) {
            return "Nenhum ingresso cadastrado";
        } else {
            return listaIngressos.stream().map(Ingresso::toString).collect(Collectors.joining("\n"));
        }
    }

    // Métodos gerenciamento filmes

    public boolean cadastrarFilme(Filme f) {
        if (listaFilmes.contains(f)) {
            return false;
        }
        listaFilmes.add(f);
        return true;
    }

    public Filme buscaFilme(int id) throws FilmeInvalidoException{
        return listaFilmes.stream().filter(f -> f.getIdFilme() == id).findFirst().orElseThrow(() -> new FilmeInvalidoException("Filme inexistente/inválido"));
    }

    public String listarFilmes() {
        if (listaFilmes.isEmpty()) {
            return "Nenhum filme cadastrado";
        } else {
            return listaFilmes.stream().map(Filme::toString).collect(Collectors.joining("\n"));
        }
    }

    public double calcularFaturamentoTotalFilme(int id) throws FilmeInvalidoException {
        Filme filme = buscaFilme(id);
        return listaIngressos.stream()
                .filter(i -> i.getSessao().getFilmeSessao().getIdFilme() == id)
                .filter(i -> i.getStatus() == StatusIngresso.UTILIZADO || i.getStatus() == StatusIngresso.DISPONIVEL)
                .mapToDouble(Ingresso::calcularValorFinal)
                .sum();
    }

    // Métodos gerenciamento sessões

    public boolean cadastrarSessao(SessaoCinema s) {
        if (listaSessoes.contains(s)) {
            return false;
        }
        listaSessoes.add(s);
        return true;
    }

    public String listarSessao() {
        if (listaSessoes.isEmpty()) {
            return "Nenhuma sessão cadastrada";
        } else {
            return listaSessoes.stream().map(SessaoCinema::toString).collect(Collectors.joining("\n"));
        }
    }

    public SessaoCinema buscaSessao(int id) throws SessaoInvalidaException{
        return listaSessoes.stream().filter(s -> s.getIdSessao() == id).findFirst().orElseThrow(() -> new SessaoInvalidaException("Sessão inválida/inexistente"));
    }

    public String listarIngressosSessao(SessaoCinema sessao) {
        if (sessao.getIngressosSessao().isEmpty()) {
            return "Nenhum ingresso cadastrado nessa sessão";
        } else {
            return sessao.getIngressosSessao().stream().map(Ingresso::toString).collect(Collectors.joining("\n"));
        }
    }

    public double calcularFaturamentoSessao(int idSessao) throws SessaoInvalidaException{
        SessaoCinema sessao = buscaSessao(idSessao);
        if (sessao.getDataHoraSessao().isBefore(LocalDateTime.now())) {
            return listaSessoes.stream()
                    .filter(s -> s.getIdSessao() == idSessao)
                    .findFirst()
                    .map(SessaoCinema::getIngressosSessao)
                    .get()
                    .stream()
                    .mapToDouble(Ingresso::calcularValorFinal).sum();
        } else {
            throw new SessaoInvalidaException("Sessão ainda não encerrada");
        }

    }



}
