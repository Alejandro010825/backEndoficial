package org.services;

import org.dao.MetodoPagoDAO;
import org.empleado.modelo.MetodoPago;
import java.sql.SQLException;
import java.util.List;

public class MetodoPagoService {

    private final MetodoPagoDAO dao;

    public MetodoPagoService() {
        this.dao = new MetodoPagoDAO();
    }

    public MetodoPagoService(MetodoPagoDAO dao) {
        this.dao = dao;
    }

    public List<MetodoPago> listarTodas() throws SQLException {
        return dao.listar();
    }

    public MetodoPago obtenerPorId(int id) throws SQLException {
        if (id <= 0) {
            return null;
        }
        return dao.obtenerPorId(id).get();
    }

    public MetodoPago crear(MetodoPago metodoPago) throws SQLException {
        validarMetodoPago(metodoPago);
        return dao.crear(metodoPago);
    }

    public boolean actualizar(int id, MetodoPago metodoPago) throws SQLException {
        if (id <= 0 || metodoPago == null) {
            return false;
        }
        validarMetodoPago(metodoPago);
        return dao.actualizar(id, metodoPago);
    }

    public boolean eliminar(int id) throws SQLException {
        if (id <= 0) {
            return false;
        }
        return dao.eliminar(id);
    }

    private void validarMetodoPago(MetodoPago metodoPago) {
        if (metodoPago == null) {
            throw new IllegalArgumentException("Método de pago no puede ser null");
        }
    }
}