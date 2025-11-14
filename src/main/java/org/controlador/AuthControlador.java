package org.controlador;

import io.javalin.http.Context;
import org.empleado.modelo.Empleado;
import org.services.AuthService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AuthControlador {

    private final AuthService service;

    public AuthControlador() {
        this.service = new AuthService();
    }

    public AuthControlador(AuthService service) {
        this.service = service;
    }

    public void login(Context ctx) {
        try {
            Map<String, String> body = ctx.bodyAsClass(Map.class);
            String usuarioLogin = body.get("usuario_login");
            String contraseña = body.get("contraseña");

            if (usuarioLogin == null || contraseña == null) {
                ctx.status(400).json(Map.of("error", "Usuario y contraseña son requeridos"));
                return;
            }

            Optional<Empleado> empleadoOpt = service.login(usuarioLogin, contraseña);

            if (empleadoOpt.isPresent()) {
                Empleado empleado = empleadoOpt.get();

                Map<String, Object> response = new HashMap<>();
                response.put("mensaje", "Login exitoso");

                Map<String, Object> empleadoData = new HashMap<>();
                empleadoData.put("id_empleado", empleado.getIdEmpleado());
                empleadoData.put("nombre", empleado.getNombre());
                empleadoData.put("apellido", empleado.getApellido());
                empleadoData.put("usuario_login", empleado.getUsuarioLogin());
                empleadoData.put("rol", empleado.getRol());

                response.put("empleado", empleadoData);

                ctx.status(200).json(response);
            } else {
                ctx.status(401).json(Map.of("error", "Credenciales inválidas"));
            }

        } catch (IllegalArgumentException ex) {
            ctx.status(400).json(Map.of("error", ex.getMessage()));
        } catch (SQLException e) {
            ctx.status(500).json(Map.of("error", "Error en el servidor: " + e.getMessage()));
        } catch (Exception ex) {
            ctx.status(400).json(Map.of("error", "Datos inválidos: " + ex.getMessage()));
        }
    }
}