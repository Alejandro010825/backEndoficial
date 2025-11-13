package org.services;

import org.dao.ConfiguracionDAO;
import org.empleado.modelo.Configuracion;
import java.sql.SQLException;
import java.util.List;

public class ConfiguracionService {

    private final ConfiguracionDAO dao;

    public ConfiguracionService() {
        this.dao = new ConfiguracionDAO();
    }

    public ConfiguracionService(ConfiguracionDAO dao) {
        this.dao = dao;
    }

    public List<Configuracion> listarTodas() throws SQLException {
        return dao.listar();
    }

    public Configuracion obtenerPorId(int id) throws SQLException {
        if (id <= 0) {
            return null;
        }
        return dao.obtenerPorId(id).orElse(null);
    }

    public Configuracion crear(Configuracion configuracion) throws SQLException {
        validarConfiguracion(configuracion);
        return dao.crear(configuracion);
    }

    public boolean actualizar(int id, Configuracion configuracion) throws SQLException {
        if (id <= 0 || configuracion == null) {
            return false;
        }
        validarConfiguracion(configuracion);
        return dao.actualizar(id, configuracion);
    }

    public boolean eliminar(int id) throws SQLException {
        if (id <= 0) {
            return false;
        }
        return dao.eliminar(id);
    }

    private void validarConfiguracion(Configuracion configuracion) {
        if (configuracion == null) {
            throw new IllegalArgumentException("Configuración no puede ser null");
        }
    }
}