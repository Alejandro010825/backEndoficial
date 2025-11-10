package org.controlador;

import io.javalin.http.Context;
import org.dao.RepartidorDAO;
import org.empleado.modelo.Repartidor;

import java.util.List;

public class RepartidorControlador {

    private final RepartidorDAO dao = new RepartidorDAO();

    public void listar(Context ctx) {
        List<Repartidor> lista = dao.listar();
        ctx.json(lista);
    }

    public void obtenerPorId(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        dao.obtenerPorId(id)
                .ifPresentOrElse(ctx::json, () -> ctx.status(404).result("Repartidor no encontrado"));
    }

    public void crear(Context ctx) {
        if (ctx.body().isEmpty()) {
            ctx.status(400).result("Body vacio");
            return;
        }

        Repartidor repartidor = ctx.bodyAsClass(Repartidor.class);
        Repartidor creado = dao.crear(repartidor);
        ctx.status(201).json(creado);
    }

    public void actualizar(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Repartidor repartidor = ctx.bodyAsClass(Repartidor.class);

        boolean ok = dao.actualizar(id, repartidor);
        if (ok) ctx.result("Repartidor actualizado");
        else ctx.status(404).result("Repartidor no encontrado");
    }

    public void eliminar(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean ok = dao.eliminar(id);
        if (ok) ctx.result("Repartidor eliminado");
        else ctx.status(404).result("Repartidor no encontrado");
    }
}