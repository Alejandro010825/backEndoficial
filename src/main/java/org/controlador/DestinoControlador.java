package org.controlador;

import io.javalin.http.Context;
import org.dao.DestinoDAO;
import org.empleado.modelo.Destino;

import java.sql.SQLException;
import java.util.List;

public class DestinoControlador {

    private final DestinoDAO dao;

    public DestinoControlador() {
        this.dao = new DestinoDAO();
    }


    public void listar(Context ctx) {
        try {
            List<Destino> lista = dao.listar();

            if (lista == null || lista.isEmpty()) {
                ctx.status(204).result("No hay destinos registrados");
            } else {
                ctx.status(200).json(lista);
            }

        } catch (SQLException e) {
            ctx.status(500).result("Error al listar destinos: " + e.getMessage());
        }
    }

    public void crear(Context ctx) {
        try {
            Destino nuevo = ctx.bodyAsClass(Destino.class);

            if (nuevo == null) {
                ctx.status(400).result("Datos de destino inválidos o vacíos");
                return;
            }

            Destino creado = dao.crear(nuevo);

            if (creado != null) {
                ctx.status(201).json(creado);
            } else {
                ctx.status(500).result("No se pudo crear el destino");
            }

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

            boolean ok = dao.actualizar(id, body);

            if (ok) {
                ctx.status(200).result("Destino actualizado correctamente");
            } else {
                ctx.status(404).result("Destino no encontrado con ID: " + id);
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser numérico");
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al actualizar destino: " + e.getMessage());
        }
    }

    public void eliminar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean ok = dao.eliminar(id);

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
