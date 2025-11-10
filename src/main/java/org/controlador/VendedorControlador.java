package org.controlador;

import io.javalin.http.Context;
import org.dao.VendedorDAO;
import org.empleado.modelo.Vendedor;

import java.util.List;

public class VendedorControlador {

    private final VendedorDAO dao = new VendedorDAO();

    public void listar(Context ctx) {
        List<Vendedor> lista = dao.listar();
        ctx.json(lista);
    }

    public void obtenerPorId(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        dao.obtenerPorId(id)
                .ifPresentOrElse(ctx::json, () -> ctx.status(404).result("Vendedor no encontrado"));
    }

    public void crear(Context ctx) {
        if (ctx.body().isEmpty()) {
            ctx.status(400).result("Body vacio");
            return;
        }

        Vendedor vendedor = ctx.bodyAsClass(Vendedor.class);
        Vendedor creado = dao.crear(vendedor);
        ctx.status(201).json(creado);
    }

    public void actualizar(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Vendedor vendedor = ctx.bodyAsClass(Vendedor.class);

        boolean ok = dao.actualizar(id, vendedor);
        if (ok) ctx.result("Vendedor actualizado");
        else ctx.status(404).result("Vendedor no encontrado");
    }

    public void eliminar(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean ok = dao.eliminar(id);
        if (ok) ctx.result("Vendedor eliminado");
        else ctx.status(404).result("Vendedor no encontrado");
    }
}