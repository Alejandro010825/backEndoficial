package org.services;

import org.dao.EmpleadoDAO;
import org.empleado.modelo.Empleado;
import java.sql.SQLException;
import java.util.List;

public class EmpleadoService {

    private final EmpleadoDAO dao;

    public EmpleadoService() {
        this.dao = new EmpleadoDAO();
    }

    public EmpleadoService(EmpleadoDAO dao) {
        this.dao = dao;
    }

    public List<Empleado> listarTodas() throws SQLException {
        return dao.listar();
    }

    public Empleado obtenerPorId(int id) throws SQLException {
        if (id <= 0) {
            return null;
        }
        return dao.obtenerPorId(id).orElse(null);
    }

    public Empleado crear(Empleado empleado) throws SQLException {
        validarEmpleado(empleado);
        return dao.crear(empleado);
    }

    public boolean actualizar(int id, Empleado empleado) throws SQLException {
        if (id <= 0 || empleado == null) {
            return false;
        }
        validarEmpleado(empleado);
        return dao.actualizar(id, empleado);
    }

    public boolean eliminar(int id) throws SQLException {
        if (id <= 0) {
            return false;
        }
        return dao.eliminar(id);
    }

    private void validarEmpleado(Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("Empleado no puede ser null");
        }
    }
}