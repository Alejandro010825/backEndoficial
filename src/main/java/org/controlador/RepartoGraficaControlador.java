package org.controlador;

import io.javalin.http.Context;
import org.empleado.modelo.RepartoGrafica;
import org.services.RepartoGraficaService;
import java.sql.SQLException;
import java.util.List;

public class RepartoGraficaControlador {

    private final RepartoGraficaService service;

    public RepartoGraficaControlador() {
        this.service = new RepartoGraficaService();
    }

    public RepartoGraficaControlador(RepartoGraficaService service) {
        this.service = service;
    }

    public void obtenerDatosGrafica(Context ctx) {
        try {
            String fechaInicio = ctx.queryParam("fechaInicio");
            String fechaFin = ctx.queryParam("fechaFin");
            String idRepartidorStr = ctx.queryParam("idRepartidor");

            if (fechaInicio == null || fechaFin == null) {
                ctx.status(400).result("Los parámetros fechaInicio y fechaFin son obligatorios");
                return;
            }

            Integer idRepartidor = null;
            if (idRepartidorStr != null && !idRepartidorStr.isEmpty()) {
                try {
                    idRepartidor = Integer.parseInt(idRepartidorStr);
                } catch (NumberFormatException e) {
                    ctx.status(400).result("El parámetro idRepartidor debe ser un número válido");
                    return;
                }
            }

            List<RepartoGrafica> datos = service.obtenerDatosGrafica(idRepartidor, fechaInicio, fechaFin);

            if (datos == null || datos.isEmpty()) {
                ctx.status(204).result("No se encontraron repartos en el rango de fechas especificado");
            } else {
                ctx.status(200).json(datos);
            }

        } catch (IllegalArgumentException ex) {
            ctx.status(400).result("Validación fallida: " + ex.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener datos de la gráfica: " + e.getMessage());
        }
    }

    public void obtenerDatosGraficaPath(Context ctx) {
        try {
            String fechaInicio = ctx.pathParam("fechaInicio");
            String fechaFin = ctx.pathParam("fechaFin");
            String idRepartidorStr = ctx.pathParam("idRepartidor");

            Integer idRepartidor = null;
            if (idRepartidorStr != null && !idRepartidorStr.isEmpty()) {
                try {
                    idRepartidor = Integer.parseInt(idRepartidorStr);
                } catch (NumberFormatException e) {
                    ctx.status(400).result("El parámetro idRepartidor debe ser un número válido");
                    return;
                }
            }

            List<RepartoGrafica> datos = service.obtenerDatosGrafica(idRepartidor, fechaInicio, fechaFin);

            if (datos == null || datos.isEmpty()) {
                ctx.status(204).result("No se encontraron repartos en el rango de fechas especificado");
            } else {
                ctx.status(200).json(datos);
            }

        } catch (IllegalArgumentException ex) {
            ctx.status(400).result("Validación fallida: " + ex.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener datos de la gráfica: " + e.getMessage());
        }
    }
}