package org.ejemplo;

import io.javalin.Javalin;
import org.rutas.RepartoGraficaRutas;
import org.rutas.*;

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
        RepartoGraficaRutas.registrar(app);
        VendedorRutas.registrar(app);
        AuthRutas.registrar(app);
        System.out.println("Servidor iniciado");
    }
}
