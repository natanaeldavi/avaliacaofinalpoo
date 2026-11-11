package model;

import java.time.LocalDateTime;

public abstract class Ingresso implements Comparable<Ingresso> {

    protected int codigo;
    protected String nomeComprador;
    protected double valorBase;
    protected StatusIngresso status;
    protected LocalDateTime dataHoraCompra;
    protected LocalDateTime dataHoraBaixa;

    public abstract double calcularValorFinal();
    public void validarNaPortaria() {

    }

    public void cancelarIngresso() {

    }

    public Ingresso(int codigo, String nomeComprador, double valorBase) {
        this.codigo = codigo;
        this.nomeComprador = nomeComprador;
        this.valorBase = valorBase;
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
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
