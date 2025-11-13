package org.services;

import org.dao.AsistenciaDAO;
import org.empleado.modelo.Asistencia;
import java.sql.SQLException;
import java.util.List;

public class AsistenciaService {

    private final AsistenciaDAO dao;

    public AsistenciaService() {
        this.dao = new AsistenciaDAO();
    }

    public AsistenciaService(AsistenciaDAO dao) {
        this.dao = dao;
    }

    public List<Asistencia> listarTodas() throws SQLException {
        return dao.listar();
    }

    public Asistencia crear(Asistencia asistencia) throws SQLException {
        validarAsistencia(asistencia);
        return dao.crear(asistencia);
    }

    public boolean actualizar(int id, Asistencia asistencia) throws SQLException {
        if (id <= 0 || asistencia == null) {
            return false;
        }
        validarAsistencia(asistencia);
        return dao.actualizar(id, asistencia);
    }

    public boolean eliminar(int id) throws SQLException {
        if (id <= 0) {
            return false;
        }
        return dao.eliminar(id);
    }

    private void validarAsistencia(Asistencia asistencia) {
        if (asistencia == null) {
            throw new IllegalArgumentException("Asistencia no puede ser null");
        }
    }

    public Asistencia obtenerPorId(int id) throws SQLException {
        if (id <= 0) {
            return null;
        }
        return dao.obtenerPorId(id).orElse(null);
    }
}