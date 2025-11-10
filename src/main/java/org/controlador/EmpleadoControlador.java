package org.controlador;

import io.javalin.http.Context;
import org.dao.EmpleadoDAO;
import org.empleado.modelo.Empleado;

import java.util.List;

public class EmpleadoControlador {

    private final EmpleadoDAO dao = new EmpleadoDAO();

    public void listar(Context ctx) {
        List<Empleado> lista = dao.listar();
        ctx.json(lista);
    }

    public void obtenerPorId(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        dao.obtenerPorId(id)
                .ifPresentOrElse(ctx::json, () -> ctx.status(404).result("Empleado no encontrado"));
    }

    public void crear(Context ctx) {
        if (ctx.body().isEmpty()) {
            ctx.status(400).result("Body vacío");
            return;
        }

        Empleado e = ctx.bodyAsClass(Empleado.class);
        Empleado creado = dao.crear(e);
        ctx.status(201).json(creado);
    }

    public void actualizar(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Empleado e = ctx.bodyAsClass(Empleado.class);

        boolean ok = dao.actualizar(id, e);
        if (ok) ctx.result("Empleado actualizado");
        else ctx.status(404).result("Empleado no encontrado");
    }

    public void eliminar(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean ok = dao.eliminar(id);
        if (ok) ctx.result("Empleado eliminado");
        else ctx.status(404).result("Empleado no encontrado");
    }
}
