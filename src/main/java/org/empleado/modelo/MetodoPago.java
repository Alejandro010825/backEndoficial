package org.empleado.modelo;

public class MetodoPago {
    private int id_metodoPago;
    private String nombre;
    private boolean estado;

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getIdMetodoPago() {
        return id_metodoPago;
    }

    public void setIdMetodoPago(int idMetodoPago) {
        this.id_metodoPago = idMetodoPago;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
