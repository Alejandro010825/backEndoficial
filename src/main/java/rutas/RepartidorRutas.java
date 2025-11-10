package rutas;

import io.javalin.Javalin;
import org.controlador.RepartidorControlador;

public class RepartidorRutas {

    public static void registrar(Javalin app) {
        RepartidorControlador controlador = new RepartidorControlador();

        app.get("/repartidor", controlador::listar);
        app.get("/repartidor/{id}", controlador::obtenerPorId);
        app.post("/repartidor", controlador::crear);
        app.put("/repartidor/{id}", controlador::actualizar);
        app.delete("/repartidor/{id}", controlador::eliminar);
    }
}