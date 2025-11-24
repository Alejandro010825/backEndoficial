package org.services;

import org.dao.RepartoDAO;
import org.empleado.modelo.RepartoGrafica;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class RepartoGraficaService {

    private final RepartoDAO dao;

    public RepartoGraficaService() {
        this.dao = new RepartoDAO();
    }

    public RepartoGraficaService(RepartoDAO dao) {
        this.dao = dao;
    }

    public List<RepartoGrafica> obtenerDatosGrafica(Integer idRepartidor, String fechaInicio, String fechaFin) throws SQLException {
        validarFechas(fechaInicio, fechaFin);

        Date inicio = Date.valueOf(fechaInicio);
        Date fin = Date.valueOf(fechaFin);

        if (idRepartidor != null && idRepartidor > 0) {
            return dao.obtenerRepartosPorFecha(idRepartidor, inicio, fin);
        } else {
            return dao.obtenerRepartosPorFecha(inicio, fin);
        }
    }

    private void validarFechas(String fechaInicio, String fechaFin) {
        if (fechaInicio == null || fechaInicio.isEmpty()) {
            throw new IllegalArgumentException("La fecha de inicio es obligatoria");
        }
        if (fechaFin == null || fechaFin.isEmpty()) {
            throw new IllegalArgumentException("La fecha de fin es obligatoria");
        }

        try {
            Date inicio = Date.valueOf(fechaInicio);
            Date fin = Date.valueOf(fechaFin);

            if (fin.before(inicio)) {
                throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use YYYY-MM-DD");
        }
    }
}