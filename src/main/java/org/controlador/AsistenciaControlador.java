package org.controlador;

import io.javalin.http.Context;
import org.empleado.modelo.Asistencia;
import org.services.AsistenciaService;

import java.sql.SQLException;
import java.util.List;

public class AsistenciaControlador {

    private final AsistenciaService service;

    public AsistenciaControlador() {
        this.service = new AsistenciaService();
    }

    public AsistenciaControlador(AsistenciaService service) {
        this.service = service;
    }

    public void listar(Context ctx) {
        try {
            List<Asistencia> lista = service.listarTodas();

            if (lista == null || lista.isEmpty()) {
                ctx.status(204).result("No hay asistencias registradas");
            } else {
                ctx.status(200).json(lista);
            }

        } catch (SQLException e) {
            ctx.status(500).result("Error al listar asistencias: " + e.getMessage());
        }
    }

    public void obtenerPorId(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Asistencia asistencia = service.obtenerPorId(id);

            if (asistencia != null) {
                ctx.status(200).json(asistencia);
            } else {
                ctx.status(404).result("Asistencia no encontrada");
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser un número");
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener asistencia: " + e.getMessage());
        }
    }

    public void crear(Context ctx) {
        try {
            Asistencia nueva = ctx.bodyAsClass(Asistencia.class);

            if (nueva == null) {
                ctx.status(400).result("Datos de asistencia inválidos o vacíos");
                return;
            }

            Asistencia creada = service.crear(nueva);

            if (creada != null) {
                ctx.status(201).json(creada);
            } else {
                ctx.status(500).result("No se pudo crear la asistencia");
            }

        } catch (IllegalArgumentException ex) {
            ctx.status(400).result("Validación fallida: " + ex.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al crear asistencia: " + e.getMessage());
        } catch (Exception ex) {
            ctx.status(400).result("JSON inválido: " + ex.getMessage());
        }
    }

    public void actualizar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Asistencia body = ctx.bodyAsClass(Asistencia.class);

            boolean ok = service.actualizar(id, body);

            if (ok) {
                ctx.status(200).result("Asistencia actualizada exitosamente");
            } else {
                ctx.status(404).result("Asistencia no encontrada con ID: " + id);
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser un número");
        } catch (IllegalArgumentException ex) {
            ctx.status(400).result("Validación fallida: " + ex.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al actualizar asistencia: " + e.getMessage());
        }
    }

    public void eliminar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean ok = service.eliminar(id);

            if (ok) {
                ctx.status(200).result("Asistencia eliminada correctamente");
            } else {
                ctx.status(404).result("Asistencia no encontrada con ID: " + id);
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser numérico");
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al eliminar asistencia: " + e.getMessage());
        }
    }
}