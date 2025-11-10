package org.empleado.modelo;

import java.sql.Date;
import java.sql.Time;

public class Asistencia {
    private int id_asistencia;
    private Integer id_repartidor;
    private Date fecha;
    private Time hora_entrada;
    private Time hora_salida;
    private String estado;
    private String observacion;

    public int getId_asistencia() {
        return id_asistencia;
    }

    public void setId_asistencia(int id_asistencia) {
        this.id_asistencia = id_asistencia;
    }

    public Integer getId_repartidor() {
        return id_repartidor;
    }

    public void setId_repartidor(Integer id_repartidor) {
        this.id_repartidor = id_repartidor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora_entrada() {
        return hora_entrada;
    }

    public void setHora_entrada(Time hora_entrada) {
        this.hora_entrada = hora_entrada;
    }

    public Time getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(Time hora_salida) {
        this.hora_salida = hora_salida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }


}
