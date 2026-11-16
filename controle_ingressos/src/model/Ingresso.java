package model;

import exception.IngressoInvalidoException;
import interfaces.Validavel;

import java.time.LocalDateTime;

public abstract class Ingresso implements Comparable<Ingresso>, Validavel {

    protected int codigo;
    protected String nomeComprador;
    protected double valorBase;
    protected StatusIngresso status;
    protected LocalDateTime dataHoraCompra;
    protected LocalDateTime dataHoraBaixa;

    public abstract double calcularValorFinal();
    public void validarNaPortaria(Ingresso ingresso) {
        if (ingresso.getStatus() == StatusIngresso.UTILIZADO) {
            throw new IngressoInvalidoException("Este ingresso já foi utilizado previamente");
        } else if (ingresso.getStatus() == StatusIngresso.CANCELADO) {
            throw new IngressoInvalidoException("Este ingresso foi cancelado!");
        } else if (ingresso.getStatus() == StatusIngresso.DISPONIVEL) {
            ingresso.setStatus(StatusIngresso.UTILIZADO);
            ingresso.setDataHoraBaixa(LocalDateTime.now());
        }
    }

    public void cancelarIngresso(Ingresso ingresso) {
        if (ingresso.getStatus() == StatusIngresso.DISPONIVEL) {
            ingresso.setStatus(StatusIngresso.CANCELADO);
            ingresso.setDataHoraBaixa(LocalDateTime.now());
        } else if (ingresso.getStatus() == StatusIngresso.UTILIZADO) {
            throw new IngressoInvalidoException("Esse ingresso já foi utilizado");
        } else if (ingresso.getStatus() == StatusIngresso.CANCELADO) {
            throw new IngressoInvalidoException("Esse ingresso já foi cancelado previamente");
        }
    }

    public Ingresso(int codigo, String nomeComprador, double valorBase) {
        this.codigo = codigo;
        this.nomeComprador = nomeComprador;
        this.valorBase = valorBase;
        this.status = StatusIngresso.DISPONIVEL;
        this.dataHoraCompra = LocalDateTime.now();
    }

    public int getCodigo() {
        return codigo;
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
        return super.toString();
    }
}
