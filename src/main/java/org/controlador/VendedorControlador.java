package org.controlador;

import io.javalin.http.Context;
import org.empleado.modelo.Vendedor;
import org.services.VendedorService;

import java.sql.SQLException;
import java.util.List;

public class VendedorControlador {

    private final VendedorService service;

    public VendedorControlador() {
        this.service = new VendedorService();
    }

    public VendedorControlador(VendedorService service) {
        this.service = service;
    }

    public void listar(Context ctx) {
        try {
            List<Vendedor> lista = service.listarTodas();

            if (lista == null || lista.isEmpty()) {
                ctx.status(204).result("No hay vendedores registrados");
            } else {
                ctx.status(200).json(lista);
            }

        } catch (SQLException e) {
            ctx.status(500).result("Error al listar vendedores: " + e.getMessage());
        }
    }

    public void obtenerPorId(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Vendedor vendedor = service.obtenerPorId(id);

            if (vendedor != null) {
                ctx.status(200).json(vendedor);
            } else {
                ctx.status(404).result("Vendedor no encontrado");
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser un número");
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener vendedor: " + e.getMessage());
        }
    }

    public void crear(Context ctx) {
        try {
            if (ctx.body().isEmpty()) {
                ctx.status(400).result("Body vacío");
                return;
            }

            Vendedor vendedor = ctx.bodyAsClass(Vendedor.class);

            if (vendedor == null) {
                ctx.status(400).result("Datos de vendedor inválidos");
                return;
            }

            Vendedor creado = service.crear(vendedor);

            if (creado != null) {
                ctx.status(201).json(creado);
            } else {
                ctx.status(500).result("No se pudo crear el vendedor");
            }

        } catch (IllegalArgumentException ex) {
            ctx.status(400).result("Validación fallida: " + ex.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al crear vendedor: " + e.getMessage());
        } catch (Exception ex) {
            ctx.status(400).result("JSON inválido: " + ex.getMessage());
        }
    }

    public void actualizar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Vendedor vendedor = ctx.bodyAsClass(Vendedor.class);

            if (vendedor == null) {
                ctx.status(400).result("Datos inválidos para actualización");
                return;
            }

            boolean ok = service.actualizar(id, vendedor);

            if (ok) {
                ctx.status(200).result("Vendedor actualizado exitosamente");
            } else {
                ctx.status(404).result("Vendedor no encontrado con ID: " + id);
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser un número");
        } catch (IllegalArgumentException ex) {
            ctx.status(400).result("Validación fallida: " + ex.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al actualizar vendedor: " + e.getMessage());
        }
    }

    public void eliminar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean ok = service.eliminar(id);

            if (ok) {
                ctx.status(200).result("Vendedor eliminado correctamente");
            } else {
                ctx.status(404).result("Vendedor no encontrado con ID: " + id);
            }

        } catch (NumberFormatException ex) {
            ctx.status(400).result("ID inválido, debe ser numérico");
        } catch (SQLException e) {
            ctx.status(500).result("Error SQL al eliminar vendedor: " + e.getMessage());
        }
    }
}