package org.empleado.modelo;

public class Huella {
    private int id_huella;
    private String tipo_persona; //vendedor-empleado-administrador
    private int id_persona;
    private int datos_huella;
    private int fecha_registro;
    private char estado;
    private int id_usuario;

    public int getId_huella() {
        return id_huella;
    }

    public void setId_huella(int id_huella) {
        this.id_huella = id_huella;
    }

    public String getTipo_persona() {
        return tipo_persona;
    }

    public void setTipo_persona(String tipo_persona) {
        this.tipo_persona = tipo_persona;
    }

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public int getDatos_huella() {
        return datos_huella;
    }

    public void setDatos_huella(int datos_huella) {
        this.datos_huella = datos_huella;
    }

    public int getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(int fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }
}
