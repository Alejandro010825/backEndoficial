package org.controlador;

import io.javalin.http.Context;
import org.empleado.modelo.Repartidor;
import org.services.RepartidorService;

import java.sql.SQLException;
import java.util.List;

public class RepartidorControlador {

    private final RepartidorService service;

    public RepartidorControlador() {
        this.service = new RepartidorService();
    }

    public RepartidorControlador(RepartidorService service) {
        this.service = service;
    }

    public void listar(Context ctx) {
        try {
            List<Repartidor> lista = service.listarTodas();

            if (lista == null || lista.isEmpty()) {
                ctx.status(204).result("No hay repartidores registrados");
            } else {
                ctx.status(200).json(lista);
            }

        } catch (SQLException e) {
            ctx.status(500).result("Error al listar repartidores: " + e.getMessage());
        }
    }

    public void obtenerPorId(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Repartidor repartidor = service.obtenerPorId(id);

            if (repartidor != null) {
                ctx.status(200).json(repartidor);
            } else {
                ctx.status(404).result("Repartidor no encontrado");
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser un número");
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener repartidor: " + e.getMessage());
        }
    }

    public void crear(Context ctx) {
        try {
            if (ctx.body().isEmpty()) {
                ctx.status(400).result("Body vacío");
                return;
            }

            Repartidor repartidor = ctx.bodyAsClass(Repartidor.class);

            if (repartidor == null) {
                ctx.status(400).result("Datos de repartidor inválidos");
                return;
            }

            Repartidor creado = service.crear(repartidor);

            if (creado != null) {
                ctx.status(201).json(creado);
            } else {
                ctx.status(500).result("No se pudo crear el repartidor");
            }

        } catch (IllegalArgumentException ex) {
            ctx.status(400).result("Validación fallida: " + ex.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al crear repartidor: " + e.getMessage());
        } catch (Exception ex) {
            ctx.status(400).result("JSON inválido: " + ex.getMessage());
        }
    }

    public void actualizar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Repartidor repartidor = ctx.bodyAsClass(Repartidor.class);

            if (repartidor == null) {
                ctx.status(400).result("Datos inválidos para actualización");
                return;
            }

            boolean ok = service.actualizar(id, repartidor);

            if (ok) {
                ctx.status(200).result("Repartidor actualizado exitosamente");
            } else {
                ctx.status(404).result("Repartidor no encontrado con ID: " + id);
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser un número");
        } catch (IllegalArgumentException ex) {
            ctx.status(400).result("Validación fallida: " + ex.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al actualizar repartidor: " + e.getMessage());
        }
    }

    public void eliminar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean ok = service.eliminar(id);

            if (ok) {
                ctx.status(200).result("Repartidor eliminado correctamente");
            } else {
                ctx.status(404).result("Repartidor no encontrado con ID: " + id);
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser numérico");
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al eliminar repartidor: " + e.getMessage());
        }
    }
}