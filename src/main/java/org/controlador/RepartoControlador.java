package org.controlador;

import io.javalin.http.Context;
import org.dao.RepartoDAO;
import org.empleado.modelo.Reparto;

import java.util.List;

public class RepartoControlador {

    private final RepartoDAO dao = new RepartoDAO();

    public void listar(Context ctx) {
        List<Reparto> lista = dao.listar();
        ctx.json(lista);
    }

    public void obtenerPorId(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        dao.obtenerPorId(id)
                .ifPresentOrElse(ctx::json, () -> ctx.status(404).result("Reparto no encontrado"));
    }

    public void crear(Context ctx) {
        if (ctx.body().isEmpty()) {
            ctx.status(400).result("Body vacio");
            return;
        }

        Reparto reparto = ctx.bodyAsClass(Reparto.class);
        Reparto creado = dao.crear(reparto);
        ctx.status(201).json(creado);
    }

    public void actualizar(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Reparto reparto = ctx.bodyAsClass(Reparto.class);

        boolean ok = dao.actualizar(id, reparto);
        if (ok) ctx.result("Reparto actualizado");
        else ctx.status(404).result("Reparto no encontrado");
    }

    public void eliminar(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean ok = dao.eliminar(id);
        if (ok) ctx.result("Reparto eliminado");
        else ctx.status(404).result("Reparto no encontrado");
    }
}