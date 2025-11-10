package rutas;

import io.javalin.Javalin;
import org.controlador.AsistenciaControlador;

public class AsistenciaRutas {

    public static void registrar(Javalin app) {
        AsistenciaControlador controlador = new AsistenciaControlador();

        app.get("/asistencias", controlador::listar);
        app.post("/asistencias", controlador::crear);
        app.put("/asistencias/{id}", controlador::actualizar);
        app.delete("/asistencias/{id}", controlador::eliminar);
    }
}
