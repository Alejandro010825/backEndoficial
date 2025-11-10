package org.project.repartidor.modelo;

public class Repartidor {
    private int id_repartidor;
    private String nombre;

    // Constructor vacío (necesario para Javalin y librerías de JSON)
    public Repartidor() {
    }

    // Constructor con parámetros
    public Repartidor(int id_repartidor, String nombre) {
        this.id_repartidor = id_repartidor;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId_repartidor() {
        return id_repartidor;
    }

    public void setId_repartidor(int id_repartidor) {
        this.id_repartidor = id_repartidor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
