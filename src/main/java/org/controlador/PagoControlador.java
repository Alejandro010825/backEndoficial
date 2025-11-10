package org.controlador;

import io.javalin.http.Context;
import org.dao.PagoDAO;
import org.empleado.modelo.Pago;

import java.util.List;

public class PagoControlador {

    private final PagoDAO dao = new PagoDAO();

    public void listar(Context ctx) {
        List<Pago> lista = dao.listar();
        ctx.json(lista);
    }

    public void obtenerPorId(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        dao.obtenerPorId(id)
                .ifPresentOrElse(ctx::json, () -> ctx.status(404).result("Pago no encontrado"));
    }

    public void obtenerPorRepartidor(Context ctx) {
        int idRepartidor = Integer.parseInt(ctx.pathParam("idRepartidor"));
        List<Pago> pagos = dao.obtenerPorRepartidor(idRepartidor);
        ctx.json(pagos);
    }

    public void crear(Context ctx) {
        if (ctx.body().isEmpty()) {
            ctx.status(400).result("Body vacio");
            return;
        }

        Pago pago = ctx.bodyAsClass(Pago.class);
        Pago creado = dao.crear(pago);
        ctx.status(201).json(creado);
    }

    public void actualizar(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Pago pago = ctx.bodyAsClass(Pago.class);

        boolean ok = dao.actualizar(id, pago);
        if (ok) ctx.result("Pago actualizado");
        else ctx.status(404).result("Pago no encontrado");
    }

    public void eliminar(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean ok = dao.eliminar(id);
        if (ok) ctx.result("Pago eliminado");
        else ctx.status(404).result("Pago no encontrado");
    }
}