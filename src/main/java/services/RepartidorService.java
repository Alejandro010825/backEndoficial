package org.services;

import org.dao.RepartidorDAO;
import org.empleado.modelo.Repartidor;
import java.sql.SQLException;
import java.util.List;

public class RepartidorService {

    private final RepartidorDAO dao;

    public RepartidorService() {
        this.dao = new RepartidorDAO();
    }

    public RepartidorService(RepartidorDAO dao) {
        this.dao = dao;
    }

    public List<Repartidor> listarTodas() throws SQLException {
        return dao.listar();
    }

    public Repartidor obtenerPorId(int id) throws SQLException {
        if (id <= 0) {
            return null;
        }
        return dao.obtenerPorId(id).get();
    }

    public Repartidor crear(Repartidor repartidor) throws SQLException {
        validarRepartidor(repartidor);
        return dao.crear(repartidor);
    }

    public boolean actualizar(int id, Repartidor repartidor) throws SQLException {
        if (id <= 0 || repartidor == null) {
            return false;
        }
        validarRepartidor(repartidor);
        return dao.actualizar(id, repartidor);
    }

    public boolean eliminar(int id) throws SQLException {
        if (id <= 0) {
            return false;
        }
        return dao.eliminar(id);
    }

    private void validarRepartidor(Repartidor repartidor) {
        if (repartidor == null) {
            throw new IllegalArgumentException("Repartidor no puede ser null");
        }
    }
}