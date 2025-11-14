package org.empleado.modelo;

public class Configuracion {
    private int id_config;
    private float tarifa_viaje;
    private float costo_falta;

    public int getId_config() {
        return id_config;
    }

    public void setId_config(int id_config) {
        this.id_config = id_config;
    }

    public float getTarifa_viaje() {
        return tarifa_viaje;
    }

    public void setTarifa_viaje(float tarifa_viaje) {
        this.tarifa_viaje = tarifa_viaje;
    }

    public float getCosto_falta() {
        return costo_falta;
    }

    public void setCosto_falta(float costo_falta) {
        this.costo_falta = costo_falta;
    }
}
