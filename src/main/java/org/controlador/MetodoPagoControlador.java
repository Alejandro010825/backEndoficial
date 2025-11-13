package org.controlador;

import io.javalin.http.Context;
import org.empleado.modelo.MetodoPago;
import org.services.MetodoPagoService;

import java.sql.SQLException;
import java.util.List;

public class MetodoPagoControlador {

    private final MetodoPagoService service;

    public MetodoPagoControlador() {
        this.service = new MetodoPagoService();
    }

    public MetodoPagoControlador(MetodoPagoService service) {
        this.service = service;
    }

    public void listar(Context ctx) {
        try {
            List<MetodoPago> lista = service.listarTodas();

            if (lista == null || lista.isEmpty()) {
                ctx.status(204).result("No hay métodos de pago registrados");
            } else {
                ctx.status(200).json(lista);
            }

        } catch (SQLException e) {
            ctx.status(500).result("Error al listar métodos de pago: " + e.getMessage());
        }
    }

    public void obtenerPorId(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            MetodoPago metodoPago = service.obtenerPorId(id);

            if (metodoPago != null) {
                ctx.status(200).json(metodoPago);
            } else {
                ctx.status(404).result("Método de pago no encontrado");
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser un número");
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener método de pago: " + e.getMessage());
        }
    }

    public void crear(Context ctx) {
        try {
            if (ctx.body().isEmpty()) {
                ctx.status(400).result("Body vacío");
                return;
            }

            MetodoPago metodo = ctx.bodyAsClass(MetodoPago.class);

            if (metodo == null) {
                ctx.status(400).result("Datos de método de pago inválidos");
                return;
            }

            MetodoPago creado = service.crear(metodo);

            if (creado != null) {
                ctx.status(201).json(creado);
            } else {
                ctx.status(500).result("No se pudo crear el método de pago");
            }

        } catch (IllegalArgumentException ex) {
            ctx.status(400).result("Validación fallida: " + ex.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al crear método de pago: " + e.getMessage());
        } catch (Exception ex) {
            ctx.status(400).result("JSON inválido: " + ex.getMessage());
        }
    }

    public void actualizar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            MetodoPago metodo = ctx.bodyAsClass(MetodoPago.class);

            if (metodo == null) {
                ctx.status(400).result("Datos inválidos para actualización");
                return;
            }

            boolean ok = service.actualizar(id, metodo);

            if (ok) {
                ctx.status(200).result("Método de pago actualizado exitosamente");
            } else {
                ctx.status(404).result("Método de pago no encontrado con ID: " + id);
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser un número");
        } catch (IllegalArgumentException ex) {
            ctx.status(400).result("Validación fallida: " + ex.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al actualizar método de pago: " + e.getMessage());
        }
    }

    public void eliminar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean ok = service.eliminar(id);

            if (ok) {
                ctx.status(200).result("Método de pago eliminado correctamente");
            } else {
                ctx.status(404).result("Método de pago no encontrado con ID: " + id);
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser numérico");
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al eliminar método de pago: " + e.getMessage());
        }
    }
}