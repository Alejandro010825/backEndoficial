package org.rutas;

import io.javalin.Javalin;
import org.controlador.RepartoGraficaControlador;

public class RepartoGraficaRutas {

    public static void registrar(Javalin app) {
        RepartoGraficaControlador controlador = new RepartoGraficaControlador();

        app.get("/repartos/grafica/{fechaInicio}/{fechaFin}", controlador::obtenerDatosGraficaPath);
        app.get("/repartos/grafica/{fechaInicio}/{fechaFin}/{idRepartidor}", controlador::obtenerDatosGraficaPath);
    }
}