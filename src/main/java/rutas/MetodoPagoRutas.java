package rutas;

import io.javalin.Javalin;
import org.controlador.MetodoPagoControlador;

public class MetodoPagoRutas {

    public static void registrar(Javalin app) {
        MetodoPagoControlador controlador = new MetodoPagoControlador();

        app.get("/metodo_pago", controlador::listar);
        app.get("/metodo_pago/{id}", controlador::obtenerPorId);
        app.post("/metodo_pago", controlador::crear);
        app.put("/metodo_pago/{id}", controlador::actualizar);
        app.delete("/metodo_pago/{id}", controlador::eliminar);

    }
}