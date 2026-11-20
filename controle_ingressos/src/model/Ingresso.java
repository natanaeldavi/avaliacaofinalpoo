package model;

import exception.IngressoInvalidoException;
import interfaces.Promocional;

import java.time.LocalDateTime;

public abstract class Ingresso implements Comparable<Ingresso>, Promocional {

    protected int codigo;
    protected String nomeFilme;
    protected String nomeComprador;
    protected double valorBase;
    protected StatusIngresso status;
    protected boolean possuiCupom;
    protected double percentualCupom;
    protected LocalDateTime dataHoraCompra;
    protected LocalDateTime dataHoraBaixa;
    private static int contadorCodigo = 1;

    public abstract double calcularValorFinal() throws IngressoInvalidoException;
    public void validarIngresso() {
        if (this.getStatus() == StatusIngresso.UTILIZADO) {
            throw new IngressoInvalidoException("Este ingresso já foi utilizado previamente");
        } else if (this.getStatus() == StatusIngresso.CANCELADO) {
            throw new IngressoInvalidoException("Este ingresso foi cancelado!");
        } else if (this.getStatus() == StatusIngresso.DISPONIVEL) {
            this.setStatus(StatusIngresso.UTILIZADO);
            this.setDataHoraBaixa(LocalDateTime.now());
        }
    }

    public void cancelarIngresso() throws IngressoInvalidoException {
        if (this.getStatus() == StatusIngresso.DISPONIVEL) {
            this.setStatus(StatusIngresso.CANCELADO);
            this.setDataHoraBaixa(LocalDateTime.now());
        } else if (this.getStatus() == StatusIngresso.UTILIZADO) {
            throw new IngressoInvalidoException("Esse ingresso já foi utilizado");
        } else if (this.getStatus() == StatusIngresso.CANCELADO) {
            throw new IngressoInvalidoException("Esse ingresso já foi cancelado previamente");
        }
    }

    public Ingresso(String nomeFilme, String nomeComprador, double valorBase) {
        this.codigo = contadorCodigo;
        contadorCodigo++;
        this.nomeFilme = nomeFilme;
        this.nomeComprador = nomeComprador;
        this.valorBase = valorBase;
        this.status = StatusIngresso.DISPONIVEL;
        this.dataHoraCompra = LocalDateTime.now();
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNomeFilme() {
        return nomeFilme;
    }

    public String getNomeComprador() {
        return nomeComprador;
    }

    public double getValorBase() {
        return valorBase;
    }

    public StatusIngresso getStatus() {
        return status;
    }

    public void setNomeFilme(String nomeFilme) {
        this.nomeFilme = nomeFilme;
    }

    public void setStatus(StatusIngresso status) {
        this.status = status;
    }

    public LocalDateTime getDataHoraCompra() {
        return dataHoraCompra;
    }

    public void setDataHoraCompra(LocalDateTime dataHoraCompra) {
        this.dataHoraCompra = dataHoraCompra;
    }

    public LocalDateTime getDataHoraBaixa() {
        return dataHoraBaixa;
    }

    public void setDataHoraBaixa(LocalDateTime dataHoraBaixa) {
        this.dataHoraBaixa = dataHoraBaixa;
    }

    @Override
    public int compareTo(Ingresso o) {
        return Integer.compare(this.codigo, o.getCodigo());
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Ingresso ingresso)) return false;
        return this.codigo == ingresso.getCodigo();
    }

    @Override
    public int hashCode() {
        return codigo;
    }

    @Override
    public String toString() {
        return "Cód: " + codigo + " | Filme: " + nomeFilme + " | Cliente: " + nomeComprador + " | Valor: " + String.format("R$ %.2f", calcularValorFinal()) + " | Status: " + status;
    }
}
