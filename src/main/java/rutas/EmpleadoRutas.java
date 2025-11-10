package rutas;

import io.javalin.Javalin;
import org.controlador.EmpleadoControlador;

public class EmpleadoRutas {
    private static final EmpleadoControlador controlador = new EmpleadoControlador();

    public static void registrar(Javalin app) {
        app.get("/empleados", controlador::listar);
        app.get("/empleados/{id}", controlador::obtenerPorId);
        app.post("/empleados", controlador::crear);
        app.put("/empleados/{id}", controlador::actualizar);
        app.delete("/empleados/{id}", controlador::eliminar);
    }
}