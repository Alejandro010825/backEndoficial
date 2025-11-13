package org.empleado.modelo;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Pago {
    private int idPago;
    private Integer idRepartidor;
    private Date periodoInicio;
    private Date periodoFin;
    private Timestamp fechaGenerado;
    private BigDecimal sueldoBase;
    private Integer viajesHechos;
    private BigDecimal sueldoViajes;
    private BigDecimal descuentos;
    private BigDecimal totalNeto;

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public Integer getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(Integer idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public Date getPeriodoInicio() {
        return periodoInicio;
    }

    public void setPeriodoInicio(Date periodoInicio) {
        this.periodoInicio = periodoInicio;
    }

    public Date getPeriodoFin() {
        return periodoFin;
    }

    public void setPeriodoFin(Date periodoFin) {
        this.periodoFin = periodoFin;
    }

    public Timestamp getFechaGenerado() {
        return fechaGenerado;
    }

    public void setFechaGenerado(Timestamp fechaGenerado) {
        this.fechaGenerado = fechaGenerado;
    }

    public BigDecimal getSueldoBase() {
        return sueldoBase;
    }

    public void setSueldoBase(BigDecimal sueldoBase) {
        this.sueldoBase = sueldoBase;
    }

    public Integer getViajesHechos() {
        return viajesHechos;
    }

    public void setViajesHechos(Integer viajesHechos) {
        this.viajesHechos = viajesHechos;
    }

    public BigDecimal getSueldoViajes() {
        return sueldoViajes;
    }

    public void setSueldoViajes(BigDecimal sueldoViajes) {
        this.sueldoViajes = sueldoViajes;
    }

    public BigDecimal getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(BigDecimal descuentos) {
        this.descuentos = descuentos;
    }

    public BigDecimal getTotalNeto() {
        return totalNeto;
    }

    public void setTotalNeto(BigDecimal totalNeto) {
        this.totalNeto = totalNeto;
    }
}