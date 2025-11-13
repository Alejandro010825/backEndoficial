package org.services;

import org.dao.DestinoDAO;
import org.empleado.modelo.Destino;
import java.sql.SQLException;
import java.util.List;

public class DestinoService {

    private final DestinoDAO dao;

    public DestinoService() {
        this.dao = new DestinoDAO();
    }

    public DestinoService(DestinoDAO dao) {
        this.dao = dao;
    }

    public List<Destino> listarTodas() throws SQLException {
        return dao.listar();
    }

    public Destino obtenerPorId(int id) throws SQLException {
        if (id <= 0) {
            return null;
        }
        return dao.obtenerPorId(id).orElse(null); // ✅ CORREGIDO
    }

    public Destino crear(Destino destino) throws SQLException {
        validarDestino(destino);
        return dao.crear(destino);
    }

    public boolean actualizar(int id, Destino destino) throws SQLException {
        if (id <= 0 || destino == null) {
            return false;
        }
        validarDestino(destino);
        return dao.actualizar(id, destino);
    }

    public boolean eliminar(int id) throws SQLException {
        if (id <= 0) {
            return false;
        }
        return dao.eliminar(id);
    }

    private void validarDestino(Destino destino) {
        if (destino == null) {
            throw new IllegalArgumentException("Destino no puede ser null");
        }
    }
}