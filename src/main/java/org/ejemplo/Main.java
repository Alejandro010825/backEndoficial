package org.ejemplo;

import io.javalin.Javalin;
import io.javalin.plugin.bundled.CorsPluginConfig;

import org.controlador.EmpleadoControlador;
import org.controlador.VendedorControlador;
import org.controlador.RepartidorControlador;
import org.controlador.DestinoControlador;
import org.controlador.RepartoControlador;
import org.controlador.PagoControlador;
import org.controlador.MetodoPagoControlador;
import org.controlador.AsistenciaControlador;
import rutas.*;

public class Main {
    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> it.anyHost());
            });
        }).start(7001);

        app.get("/", ctx -> ctx.result("puerto 7001"));

        // Registrar rutas
        AsistenciaRutas.registrar(app);
        ConfiguracionRutas.registrar(app);
        DestinoRutas.registrar(app);
        EmpleadoRutas.registrar(app);
        MetodoPagoRutas.registrar(app);
        PagoRutas.registrar(app);
        RepartidorRutas.registrar(app);
        RepartoRutas.registrar(app);
        VendedorRutas.registrar(app);

        System.out.println("Servidor iniciado");
    }
}
