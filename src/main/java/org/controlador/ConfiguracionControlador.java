package org.controlador;

import io.javalin.http.Context;
import org.empleado.modelo.Configuracion;
import org.services.ConfiguracionService;

import java.sql.SQLException;
import java.util.List;

public class ConfiguracionControlador {

    private final ConfiguracionService service;

    public ConfiguracionControlador() {
        this.service = new ConfiguracionService();
    }

    public ConfiguracionControlador(ConfiguracionService service) {
        this.service = service;
    }

    public void listar(Context ctx) {
        try {
            List<Configuracion> lista = service.listarTodas();

            if (lista == null || lista.isEmpty()) {
                ctx.status(204).result("No hay configuraciones registradas");
            } else {
                ctx.status(200).json(lista);
            }

        } catch (SQLException e) {
            ctx.status(500).result("Error al listar configuraciones: " + e.getMessage());
        }
    }

    public void obtenerPorId(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Configuracion configuracion = service.obtenerPorId(id);

            if (configuracion != null) {
                ctx.status(200).json(configuracion);
            } else {
                ctx.status(404).result("Configuración no encontrada");
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser un número");
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener configuración: " + e.getMessage());
        }
    }

    public void crear(Context ctx) {
        try {
            if (ctx.body().isEmpty()) {
                ctx.status(400).result("Body vacío");
                return;
            }

            Configuracion config = ctx.bodyAsClass(Configuracion.class);

            if (config == null) {
                ctx.status(400).result("Datos de configuración inválidos");
                return;
            }

            Configuracion creada = service.crear(config);

            if (creada != null) {
                ctx.status(201).json(creada);
            } else {
                ctx.status(500).result("No se pudo crear la configuración");
            }

        } catch (IllegalArgumentException ex) {
            ctx.status(400).result("Validación fallida: " + ex.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al crear configuración: " + e.getMessage());
        } catch (Exception ex) {
            ctx.status(400).result("JSON inválido: " + ex.getMessage());
        }
    }

    public void actualizar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Configuracion config = ctx.bodyAsClass(Configuracion.class);

            if (config == null) {
                ctx.status(400).result("Datos inválidos para actualización");
                return;
            }

            boolean ok = service.actualizar(id, config);

            if (ok) {
                ctx.status(200).result("Configuración actualizada exitosamente");
            } else {
                ctx.status(404).result("Configuración no encontrada con ID: " + id);
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser un número");
        } catch (IllegalArgumentException ex) {
            ctx.status(400).result("Validación fallida: " + ex.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al actualizar configuración: " + e.getMessage());
        }
    }

    public void eliminar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean ok = service.eliminar(id);

            if (ok) {
                ctx.status(200).result("Configuración eliminada correctamente");
            } else {
                ctx.status(404).result("Configuración no encontrada con ID: " + id);
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser numérico");
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al eliminar configuración: " + e.getMessage());
        }
    }
}