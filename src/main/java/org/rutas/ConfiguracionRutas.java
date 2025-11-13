package org.rutas;

import io.javalin.Javalin;
import org.controlador.ConfiguracionControlador;

public class ConfiguracionRutas {

    public static void registrar(Javalin app) {
        ConfiguracionControlador controlador = new ConfiguracionControlador();

        app.get("/configuracion", controlador::listar);
        app.get("/configuracion/{id}", controlador::obtenerPorId);
        app.post("/configuracion", controlador::crear);
        app.put("/configuracion/{id}", controlador::actualizar);
        app.delete("/configuracion/{id}", controlador::eliminar);
    }
}