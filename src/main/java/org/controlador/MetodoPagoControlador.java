package org.controlador;

import io.javalin.http.Context;
import org.dao.MetodoPagoDAO;
import org.empleado.modelo.MetodoPago;

import java.util.List;

public class MetodoPagoControlador {

    private final MetodoPagoDAO dao = new MetodoPagoDAO();

    public void listar(Context ctx) {
        List<MetodoPago> lista = dao.listar();
        ctx.json(lista);
    }

    public void obtenerPorId(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        dao.obtenerPorId(id)
                .ifPresentOrElse(ctx::json, () -> ctx.status(404).result("Método de pago no encontrado"));
    }

    public void crear(Context ctx) {
        if (ctx.body().isEmpty()) {
            ctx.status(400).result("Body vacio");
            return;
        }

        MetodoPago metodo = ctx.bodyAsClass(MetodoPago.class);
        MetodoPago creado = dao.crear(metodo);
        ctx.status(201).json(creado);
    }

    public void actualizar(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        MetodoPago metodo = ctx.bodyAsClass(MetodoPago.class);

        boolean ok = dao.actualizar(id, metodo);
        if (ok) ctx.result("Método de pago actualizado");
        else ctx.status(404).result("Método de pago no encontrado");
    }

    public void eliminar(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean ok = dao.eliminar(id);
        if (ok) ctx.result("Método de pago eliminado");
        else ctx.status(404).result("Método de pago no encontrado");
    }
}