package org.services;

import org.dao.EmpleadoDAO;
import org.empleado.modelo.Empleado;

import java.sql.SQLException;
import java.util.Optional;

public class AuthService {

    private final EmpleadoDAO dao;

    public AuthService() {
        this.dao = new EmpleadoDAO();
    }

    public AuthService(EmpleadoDAO dao) {
        this.dao = dao;
    }

    public Optional<Empleado> login(String usuarioLogin, String contraseña) throws SQLException {
        if (usuarioLogin == null || usuarioLogin.isEmpty()) {
            throw new IllegalArgumentException("Usuario es requerido");
        }
        if (contraseña == null || contraseña.isEmpty()) {
            throw new IllegalArgumentException("Contraseña es requerida");
        }

        Optional<Empleado> empleadoOpt = dao.obtenerPorUsuario(usuarioLogin);

        if (empleadoOpt.isPresent()) {
            Empleado empleado = empleadoOpt.get();

            // Comparar contraseñas en texto plano
            if (empleado.getContraseña().equals(contraseña)) {
                return Optional.of(empleado);
            }
        }

        return Optional.empty();
    }
}