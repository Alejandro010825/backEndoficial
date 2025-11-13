package org.rutas;

import io.javalin.Javalin;
import org.controlador.RepartoControlador;

public class RepartoRutas {

    public static void registrar(Javalin app) {
        RepartoControlador controlador = new RepartoControlador();

        app.get("/reparto", controlador::listar);
        app.get("/reparto/{id}", controlador::obtenerPorId);
        app.post("/reparto", controlador::crear);
        app.put("/reparto/{id}", controlador::actualizar);
        app.delete("/reparto/{id}", controlador::eliminar);
    }
}