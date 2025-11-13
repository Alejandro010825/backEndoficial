package org.rutas;

import io.javalin.Javalin;
import org.controlador.DestinoControlador;

public class DestinoRutas {

    private static final DestinoControlador destinoControlador = new DestinoControlador();

    public static void registrar(Javalin app) {
        app.get("/destinos", destinoControlador::listar);
        app.post("/destinos", destinoControlador::crear);
        app.put("/destinos/{id}", destinoControlador::actualizar);
        app.delete("/destinos/{id}", destinoControlador::eliminar);
        app.get("/destinos/{id}", destinoControlador::obtenerPorId);
    }
}
