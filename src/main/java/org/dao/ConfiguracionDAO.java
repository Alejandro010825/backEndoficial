package org.dao;

import org.empleado.modelo.Configuracion;
import org.project.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConfiguracionDAO {

    public List<Configuracion> listar() {
        List<Configuracion> configuraciones = new ArrayList<>();
        String sql = "SELECT * FROM configuracion";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Configuracion config = mapearConfiguracion(rs);
                configuraciones.add(config);
            }

            System.out.println("Configuraciones encontradas: " + configuraciones.size());

        } catch (SQLException e) {
            System.err.println("Error al listar configuraciones: " + e.getMessage());
            e.printStackTrace();
        }

        return configuraciones;
    }

    public Optional<Configuracion> obtenerPorId(int id) {
        String sql = "SELECT * FROM configuracion WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearConfiguracion(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener configuración: " + e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Configuracion crear(Configuracion config) {
        String sql = "INSERT INTO configuracion (sueldo_base, tarifa_viaje, costo_falta) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setFloat(1, config.getSueldo_base());
            stmt.setFloat(2, config.getTarifa_viaje());
            stmt.setFloat(3, config.getCosto_falta());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                config.setId_config(rs.getInt(1));
            }

            System.out.println("Configuración creada con ID: " + config.getId_config());

        } catch (SQLException e) {
            System.err.println("Error al crear configuración: " + e.getMessage());
            e.printStackTrace();
        }

        return config;
    }

    public boolean actualizar(int id, Configuracion config) {
        String sql = "UPDATE configuracion SET sueldo_base=?, tarifa_viaje=?, costo_falta=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setFloat(1, config.getSueldo_base());
            stmt.setFloat(2, config.getTarifa_viaje());
            stmt.setFloat(3, config.getCosto_falta());
            stmt.setInt(4, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Configuración actualizada");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar configuración: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM configuracion WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Configuración eliminada");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar configuración: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    private Configuracion mapearConfiguracion(ResultSet rs) throws SQLException {
        Configuracion config = new Configuracion();
        config.setId_config(rs.getInt("id"));
        config.setSueldo_base(rs.getFloat("sueldo_base"));
        config.setTarifa_viaje(rs.getFloat("tarifa_viaje"));
        config.setCosto_falta(rs.getFloat("costo_falta"));
        return config;
    }
}