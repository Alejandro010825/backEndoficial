package org.rutas;

import io.javalin.Javalin;
import org.controlador.PagoControlador;

public class PagoRutas {

    public static void registrar(Javalin app) {
        PagoControlador controlador = new PagoControlador();

        app.get("/pago", controlador::listar);
        app.get("/pago/{id}", controlador::obtenerPorId);
        app.get("/pago/repartidor/{idRepartidor}", controlador::obtenerPorRepartidor);
        app.post("/pago", controlador::crear);
        app.put("/pago/{id}", controlador::actualizar);
        app.delete("/pago/{id}", controlador::eliminar);
    }
}