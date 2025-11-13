package org.services;

import org.dao.PagoDAO;
import org.empleado.modelo.Pago;
import java.sql.SQLException;
import java.util.List;

public class PagoService {

    private final PagoDAO dao;

    public PagoService() {
        this.dao = new PagoDAO();
    }

    public PagoService(PagoDAO dao) {
        this.dao = dao;
    }

    public List<Pago> listarTodas() throws SQLException {
        return dao.listar();
    }

    public Pago obtenerPorId(int id) throws SQLException {
        if (id <= 0) {
            return null;
        }
        return dao.obtenerPorId(id).orElse(null); // ✅ CORREGIDO
    }

    public List<Pago> obtenerPorRepartidor(int idRepartidor) throws SQLException {
        if (idRepartidor <= 0) {
            return List.of();
        }
        return dao.obtenerPorRepartidor(idRepartidor);
    }

    public Pago crear(Pago pago) throws SQLException {
        validarPago(pago);
        return dao.crear(pago);
    }

    public boolean actualizar(int id, Pago pago) throws SQLException {
        if (id <= 0 || pago == null) {
            return false;
        }
        validarPago(pago);
        return dao.actualizar(id, pago);
    }

    public boolean eliminar(int id) throws SQLException {
        if (id <= 0) {
            return false;
        }
        return dao.eliminar(id);
    }

    private void validarPago(Pago pago) {
        if (pago == null) {
            throw new IllegalArgumentException("Pago no puede ser null");
        }
    }
}