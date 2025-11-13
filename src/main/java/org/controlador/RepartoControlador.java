package org.controlador;

import io.javalin.http.Context;
import org.empleado.modelo.Reparto;
import org.services.RepartoService;

import java.sql.SQLException;
import java.util.List;

public class RepartoControlador {

    private final RepartoService service;

    public RepartoControlador() {
        this.service = new RepartoService();
    }

    public RepartoControlador(RepartoService service) {
        this.service = service;
    }

    public void listar(Context ctx) {
        try {
            List<Reparto> lista = service.listarTodas();

            if (lista == null || lista.isEmpty()) {
                ctx.status(204).result("No hay repartos registrados");
            } else {
                ctx.status(200).json(lista);
            }

        } catch (SQLException e) {
            ctx.status(500).result("Error al listar repartos: " + e.getMessage());
        }
    }

    public void obtenerPorId(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Reparto reparto = service.obtenerPorId(id);

            if (reparto != null) {
                ctx.status(200).json(reparto);
            } else {
                ctx.status(404).result("Reparto no encontrado");
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser un número");
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener reparto: " + e.getMessage());
        }
    }

    public void crear(Context ctx) {
        try {
            if (ctx.body().isEmpty()) {
                ctx.status(400).result("Body vacío");
                return;
            }

            Reparto reparto = ctx.bodyAsClass(Reparto.class);

            if (reparto == null) {
                ctx.status(400).result("Datos de reparto inválidos");
                return;
            }

            Reparto creado = service.crear(reparto);

            if (creado != null) {
                ctx.status(201).json(creado);
            } else {
                ctx.status(500).result("No se pudo crear el reparto");
            }

        } catch (IllegalArgumentException ex) {
            ctx.status(400).result("Validación fallida: " + ex.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al crear reparto: " + e.getMessage());
        } catch (Exception ex) {
            ctx.status(400).result("JSON inválido: " + ex.getMessage());
        }
    }

    public void actualizar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Reparto reparto = ctx.bodyAsClass(Reparto.class);

            if (reparto == null) {
                ctx.status(400).result("Datos inválidos para actualización");
                return;
            }

            boolean ok = service.actualizar(id, reparto);

            if (ok) {
                ctx.status(200).result("Reparto actualizado exitosamente");
            } else {
                ctx.status(404).result("Reparto no encontrado con ID: " + id);
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser un número");
        } catch (IllegalArgumentException ex) {
            ctx.status(400).result("Validación fallida: " + ex.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al actualizar reparto: " + e.getMessage());
        }
    }

    public void eliminar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean ok = service.eliminar(id);

            if (ok) {
                ctx.status(200).result("Reparto eliminado correctamente");
            } else {
                ctx.status(404).result("Reparto no encontrado con ID: " + id);
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser numérico");
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al eliminar reparto: " + e.getMessage());
        }
    }
}