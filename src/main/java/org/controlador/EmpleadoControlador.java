package org.controlador;

import io.javalin.http.Context;
import org.empleado.modelo.Empleado;
import org.services.EmpleadoService;

import java.sql.SQLException;
import java.util.List;

public class EmpleadoControlador {

    private final EmpleadoService service;

    public EmpleadoControlador() {
        this.service = new EmpleadoService();
    }

    public EmpleadoControlador(EmpleadoService service) {
        this.service = service;
    }

    public void listar(Context ctx) {
        try {
            List<Empleado> lista = service.listarTodas();

            if (lista == null || lista.isEmpty()) {
                ctx.status(204).result("No hay empleados registrados");
            } else {
                ctx.status(200).json(lista);
            }

        } catch (SQLException e) {
            ctx.status(500).result("Error al listar empleados: " + e.getMessage());
        }
    }

    public void obtenerPorId(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Empleado empleado = service.obtenerPorId(id);

            if (empleado != null) {
                ctx.status(200).json(empleado);
            } else {
                ctx.status(404).result("Empleado no encontrado");
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser un número");
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener empleado: " + e.getMessage());
        }
    }

    public void crear(Context ctx) {
        try {
            if (ctx.body().isEmpty()) {
                ctx.status(400).result("Body vacío");
                return;
            }

            Empleado empleado = ctx.bodyAsClass(Empleado.class);

            if (empleado == null) {
                ctx.status(400).result("Datos de empleado inválidos");
                return;
            }

            Empleado creado = service.crear(empleado);

            if (creado != null) {
                ctx.status(201).json(creado);
            } else {
                ctx.status(500).result("No se pudo crear el empleado");
            }

        } catch (IllegalArgumentException ex) {
            ctx.status(400).result("Validación fallida: " + ex.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al crear empleado: " + e.getMessage());
        } catch (Exception ex) {
            ctx.status(400).result("JSON inválido: " + ex.getMessage());
        }
    }

    public void actualizar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Empleado empleado = ctx.bodyAsClass(Empleado.class);

            if (empleado == null) {
                ctx.status(400).result("Datos inválidos para actualización");
                return;
            }

            boolean ok = service.actualizar(id, empleado);

            if (ok) {
                ctx.status(200).result("Empleado actualizado exitosamente");
            } else {
                ctx.status(404).result("Empleado no encontrado con ID: " + id);
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser un número");
        } catch (IllegalArgumentException ex) {
            ctx.status(400).result("Validación fallida: " + ex.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al actualizar empleado: " + e.getMessage());
        }
    }

    public void eliminar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean ok = service.eliminar(id);

            if (ok) {
                ctx.status(200).result("Empleado eliminado correctamente");
            } else {
                ctx.status(404).result("Empleado no encontrado con ID: " + id);
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser numérico");
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al eliminar empleado: " + e.getMessage());
        }
    }
}