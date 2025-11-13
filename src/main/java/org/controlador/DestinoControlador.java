package org.controlador;

import io.javalin.http.Context;
import org.empleado.modelo.Destino;
import org.services.DestinoService;

import java.sql.SQLException;
import java.util.List;

public class DestinoControlador {

    private final DestinoService service;

    public DestinoControlador() {
        this.service = new DestinoService();
    }

    public DestinoControlador(DestinoService service) {
        this.service = service;
    }

    public void listar(Context ctx) {
        try {
            List<Destino> lista = service.listarTodas();

            if (lista == null || lista.isEmpty()) {
                ctx.status(204).result("No hay destinos registrados");
            } else {
                ctx.status(200).json(lista);
            }

        } catch (SQLException e) {
            ctx.status(500).result("Error al listar destinos: " + e.getMessage());
        }
    }

    public void obtenerPorId(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Destino destino = service.obtenerPorId(id);

            if (destino != null) {
                ctx.status(200).json(destino);
            } else {
                ctx.status(404).result("Destino no encontrado");
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser un número");
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener destino: " + e.getMessage());
        }
    }

    public void crear(Context ctx) {
        try {
            Destino nuevo = ctx.bodyAsClass(Destino.class);

            if (nuevo == null) {
                ctx.status(400).result("Datos de destino inválidos o vacíos");
                return;
            }

            Destino creado = service.crear(nuevo);

            if (creado != null) {
                ctx.status(201).json(creado);
            } else {
                ctx.status(500).result("No se pudo crear el destino");
            }

        } catch (IllegalArgumentException ex) {
            ctx.status(400).result("Validación fallida: " + ex.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al crear destino: " + e.getMessage());
        } catch (Exception ex) {
            ctx.status(400).result("JSON inválido: " + ex.getMessage());
        }
    }

    public void actualizar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Destino body = ctx.bodyAsClass(Destino.class);

            if (body == null) {
                ctx.status(400).result("Datos inválidos para actualización");
                return;
            }

            boolean ok = service.actualizar(id, body);

            if (ok) {
                ctx.status(200).result("Destino actualizado correctamente");
            } else {
                ctx.status(404).result("Destino no encontrado con ID: " + id);
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser numérico");
        } catch (IllegalArgumentException ex) {
            ctx.status(400).result("Validación fallida: " + ex.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al actualizar destino: " + e.getMessage());
        }
    }

    public void eliminar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean ok = service.eliminar(id);

            if (ok) {
                ctx.status(200).result("Destino eliminado correctamente");
            } else {
                ctx.status(404).result("Destino no encontrado con ID: " + id);
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser un número");
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al eliminar destino: " + e.getMessage());
        }
    }
}