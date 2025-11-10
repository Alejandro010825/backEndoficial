package org.dao;

import org.empleado.modelo.Repartidor;
import org.project.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepartidorDAO {

    public List<Repartidor> listar() {
        List<Repartidor> repartidores = new ArrayList<>();
        String sql = "SELECT * FROM repartidor";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Repartidor repartidor = mapearRepartidor(rs);
                repartidores.add(repartidor);
            }

            System.out.println("Repartidores encontrados: " + repartidores.size());

        } catch (SQLException e) {
            System.err.println("Error al listar repartidores: " + e.getMessage());
            e.printStackTrace();
        }

        return repartidores;
    }

    public Optional<Repartidor> obtenerPorId(int id) {
        String sql = "SELECT * FROM repartidor WHERE id_repartidor = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearRepartidor(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener repartidor: " + e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Repartidor crear(Repartidor repartidor) {
        String sql = "INSERT INTO repartidor (nombre) VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, repartidor.getNombre());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                repartidor.setId_repartidor(rs.getInt(1));
            }

            System.out.println("Repartidor creado con ID: " + repartidor.getId_repartidor());

        } catch (SQLException e) {
            System.err.println("Error al crear repartidor: " + e.getMessage());
            e.printStackTrace();
        }

        return repartidor;
    }

    public boolean actualizar(int id, Repartidor repartidor) {
        String sql = "UPDATE repartidor SET nombre=? WHERE id_repartidor=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, repartidor.getNombre());
            stmt.setInt(2, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Repartidor actualizado");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar repartidor: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM repartidor WHERE id_repartidor=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Repartidor eliminado");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar repartidor: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    private Repartidor mapearRepartidor(ResultSet rs) throws SQLException {
        Repartidor repartidor = new Repartidor();
        repartidor.setId_repartidor(rs.getInt("id_repartidor"));
        repartidor.setNombre(rs.getString("nombre"));
        return repartidor;
    }
}