package org.empleado.modelo;

import java.sql.Date;

public class RepartoGrafica {
    private Date fecha;
    private int cantidadRepartos;
    private Integer idRepartidor;

    public RepartoGrafica() {
    }

    public RepartoGrafica(Date fecha, int cantidadRepartos, Integer idRepartidor) {
        this.fecha = fecha;
        this.cantidadRepartos = cantidadRepartos;
        this.idRepartidor = idRepartidor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCantidadRepartos() {
        return cantidadRepartos;
    }

    public void setCantidadRepartos(int cantidadRepartos) {
        this.cantidadRepartos = cantidadRepartos;
    }

    public Integer getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(Integer idRepartidor) {
        this.idRepartidor = idRepartidor;
    }
}