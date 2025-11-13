package org.services;

import org.dao.RepartoDAO;
import org.empleado.modelo.Reparto;
import java.sql.SQLException;
import java.util.List;

public class RepartoService {

    private final RepartoDAO dao;

    public RepartoService() {
        this.dao = new RepartoDAO();
    }

    public RepartoService(RepartoDAO dao) {
        this.dao = dao;
    }

    public List<Reparto> listarTodas() throws SQLException {
        return dao.listar();
    }

    public Reparto obtenerPorId(int id) throws SQLException {
        if (id <= 0) {
            return null;
        }
        return dao.obtenerPorId(id).orElse(null); // ✅ CORREGIDO
    }

    public Reparto crear(Reparto reparto) throws SQLException {
        validarReparto(reparto);
        return dao.crear(reparto);
    }

    public boolean actualizar(int id, Reparto reparto) throws SQLException {
        if (id <= 0 || reparto == null) {
            return false;
        }
        validarReparto(reparto);
        return dao.actualizar(id, reparto);
    }

    public boolean eliminar(int id) throws SQLException {
        if (id <= 0) {
            return false;
        }
        return dao.eliminar(id);
    }

    private void validarReparto(Reparto reparto) {
        if (reparto == null) {
            throw new IllegalArgumentException("Reparto no puede ser null");
        }
    }
}