package org.rutas;

import io.javalin.Javalin;
import org.controlador.AuthControlador;

public class AuthRutas {

    public static void registrar(Javalin app) {
        AuthControlador controlador = new AuthControlador();

        app.post("/login", controlador::login);
    }
}