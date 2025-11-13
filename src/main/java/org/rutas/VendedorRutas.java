package org.rutas;

import io.javalin.Javalin;
import org.controlador.VendedorControlador;

public class VendedorRutas {

    public static void registrar(Javalin app) {
        VendedorControlador controlador = new VendedorControlador();

        app.get("/vendedor", controlador::listar);
        app.get("/vendedor/{id}", controlador::obtenerPorId);
        app.post("/vendedor", controlador::crear);
        app.put("/vendedor/{id}", controlador::actualizar);
        app.delete("/vendedor/{id}", controlador::eliminar);
    }
}