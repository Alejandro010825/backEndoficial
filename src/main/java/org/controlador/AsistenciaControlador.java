package org.controlador;

import io.javalin.http.Context;
import org.dao.AsistenciaDAO;
import org.empleado.modelo.Asistencia;

import java.sql.SQLException;
import java.util.List;

public class AsistenciaControlador {

    private final AsistenciaDAO dao;

    public AsistenciaControlador() {
        this.dao = new AsistenciaDAO();
    }

    public void listar(Context ctx) {
        try {
            List<Asistencia> lista = dao.listar();

            if (lista == null || lista.isEmpty()) {
                ctx.status(204).result("No hay asistencias registradas");
            } else {
                ctx.status(200).json(lista);
            }

        } catch (SQLException e) {
            ctx.status(500).result("Error al listar asistencias: " + e.getMessage());
        }
    }

    public void crear(Context ctx) {
        try {
            Asistencia nueva = ctx.bodyAsClass(Asistencia.class);

            if (nueva == null) {
                ctx.status(400).result("Datos de asistencia invalidos o vacios");
                return;
            }

            Asistencia creada = dao.crear(nueva);

            if (creada != null) {
                ctx.status(201).json(creada);
            } else {
                ctx.status(500).result("No se pudo crear la asistencia");
            }

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

            boolean ok = dao.actualizar(id, body);

            if (ok) ctx.status(200).result("Asistencia actualizada exitosamente");
            else ctx.status(404).result("Asistencia no encontrada con ID: " + id);

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser un número");
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al actualizar asistencia: " + e.getMessage());
        }
    }

    public void eliminar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean ok = dao.eliminar(id);

            if (ok) ctx.status(200).result("Asistencia eliminada correctamente");
            else ctx.status(404).result("Asistencia no encontrada con ID: " + id);

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser numérico");
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al eliminar asistencia: " + e.getMessage());
        }
    }
}
