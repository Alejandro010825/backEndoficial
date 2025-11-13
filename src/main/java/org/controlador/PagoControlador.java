package org.controlador;

import io.javalin.http.Context;
import org.empleado.modelo.Pago;
import org.services.PagoService;

import java.sql.SQLException;
import java.util.List;

public class PagoControlador {

    private final PagoService service;

    public PagoControlador() {
        this.service = new PagoService();
    }

    public PagoControlador(PagoService service) {
        this.service = service;
    }

    public void listar(Context ctx) {
        try {
            List<Pago> lista = service.listarTodas();

            if (lista == null || lista.isEmpty()) {
                ctx.status(204).result("No hay pagos registrados");
            } else {
                ctx.status(200).json(lista);
            }

        } catch (SQLException e) {
            ctx.status(500).result("Error al listar pagos: " + e.getMessage());
        }
    }

    public void obtenerPorId(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Pago pago = service.obtenerPorId(id);

            if (pago != null) {
                ctx.status(200).json(pago);
            } else {
                ctx.status(404).result("Pago no encontrado");
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser un número");
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener pago: " + e.getMessage());
        }
    }

    public void obtenerPorRepartidor(Context ctx) {
        try {
            int idRepartidor = Integer.parseInt(ctx.pathParam("idRepartidor"));
            List<Pago> pagos = service.obtenerPorRepartidor(idRepartidor);

            if (pagos == null || pagos.isEmpty()) {
                ctx.status(204).result("No hay pagos para este repartidor");
            } else {
                ctx.status(200).json(pagos);
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID de repartidor inválido, debe ser un número");
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener pagos por repartidor: " + e.getMessage());
        }
    }

    public void crear(Context ctx) {
        try {
            if (ctx.body().isEmpty()) {
                ctx.status(400).result("Body vacío");
                return;
            }

            Pago pago = ctx.bodyAsClass(Pago.class);

            if (pago == null) {
                ctx.status(400).result("Datos de pago inválidos");
                return;
            }

            Pago creado = service.crear(pago);

            if (creado != null) {
                ctx.status(201).json(creado);
            } else {
                ctx.status(500).result("No se pudo crear el pago");
            }

        } catch (IllegalArgumentException ex) {
            ctx.status(400).result("Validación fallida: " + ex.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al crear pago: " + e.getMessage());
        } catch (Exception ex) {
            ctx.status(400).result("JSON inválido: " + ex.getMessage());
        }
    }

    public void actualizar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Pago pago = ctx.bodyAsClass(Pago.class);

            if (pago == null) {
                ctx.status(400).result("Datos inválidos para actualización");
                return;
            }

            boolean ok = service.actualizar(id, pago);

            if (ok) {
                ctx.status(200).result("Pago actualizado exitosamente");
            } else {
                ctx.status(404).result("Pago no encontrado con ID: " + id);
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser un número");
        } catch (IllegalArgumentException ex) {
            ctx.status(400).result("Validación fallida: " + ex.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al actualizar pago: " + e.getMessage());
        }
    }

    public void eliminar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean ok = service.eliminar(id);

            if (ok) {
                ctx.status(200).result("Pago eliminado correctamente");
            } else {
                ctx.status(404).result("Pago no encontrado con ID: " + id);
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser numérico");
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al eliminar pago: " + e.getMessage());
        }
    }
}