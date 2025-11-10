package org.empleado.modelo;

public class Destino {
    private int id_destino;
    private String lugar;
    private String direccion;

    public int getIdDestino() {
        return id_destino;
    }

    public void setIdDestino(int idDestino) {
        this.id_destino = idDestino;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
