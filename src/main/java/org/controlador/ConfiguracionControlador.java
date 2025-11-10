package org.controlador;

import io.javalin.http.Context;
import org.dao.ConfiguracionDAO;
import org.empleado.modelo.Configuracion;

import java.util.List;

public class ConfiguracionControlador {

    private final ConfiguracionDAO dao = new ConfiguracionDAO();

    public void listar(Context ctx) {
        List<Configuracion> lista = dao.listar();
        ctx.json(lista);
    }

    public void obtenerPorId(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        dao.obtenerPorId(id)
                .ifPresentOrElse(ctx::json, () -> ctx.status(404).result("Configuración no encontrada"));
    }

    public void crear(Context ctx) {
        if (ctx.body().isEmpty()) {
            ctx.status(400).result("Body vacío");
            return;
        }

        Configuracion config = ctx.bodyAsClass(Configuracion.class);
        Configuracion creada = dao.crear(config);
        ctx.status(201).json(creada);
    }

    public void actualizar(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Configuracion config = ctx.bodyAsClass(Configuracion.class);

        boolean ok = dao.actualizar(id, config);
        if (ok) ctx.result("Configuración actualizada");
        else ctx.status(404).result("Configuración no encontrada");
    }

    public void eliminar(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean ok = dao.eliminar(id);
        if (ok) ctx.result("Configuración eliminada");
        else ctx.status(404).result("Configuración no encontrada");
    }
}