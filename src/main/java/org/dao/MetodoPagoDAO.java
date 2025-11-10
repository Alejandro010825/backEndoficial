package org.dao;

import org.empleado.modelo.MetodoPago;
import org.project.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MetodoPagoDAO {

    public List<MetodoPago> listar() {
        List<MetodoPago> metodosPago = new ArrayList<>();
        String sql = "SELECT * FROM metodo_pago";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                MetodoPago metodo = mapearMetodoPago(rs);
                metodosPago.add(metodo);
            }

            System.out.println("Métodos de pago encontrados: " + metodosPago.size());

        } catch (SQLException e) {
            System.err.println("Error al listar metodos de pago: " + e.getMessage());
            e.printStackTrace();
        }

        return metodosPago;
    }

    public Optional<MetodoPago> obtenerPorId(int id) {
        String sql = "SELECT * FROM metodo_pago WHERE id_metodo_pago = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearMetodoPago(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener metodo de pago: " + e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public MetodoPago crear(MetodoPago metodo) {
        String sql = "INSERT INTO metodo_pago (nombre, activo) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, metodo.getNombre());
            stmt.setBoolean(2, metodo.isEstado());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                metodo.setIdMetodoPago(rs.getInt(1));
            }

            System.out.println("Metodo de pago creado con ID: " + metodo.getIdMetodoPago());

        } catch (SQLException e) {
            System.err.println("Error al crear metodo de pago: " + e.getMessage());
            e.printStackTrace();
        }

        return metodo;
    }

    public boolean actualizar(int id, MetodoPago metodo) {
        String sql = "UPDATE metodo_pago SET nombre=?, activo=? WHERE id_metodo_pago=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, metodo.getNombre());
            stmt.setBoolean(2, metodo.isEstado());
            stmt.setInt(3, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Metodo de pago actualizado");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar metodo de pago: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM metodo_pago WHERE id_metodo_pago=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Metodo de pago eliminado");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar metodo de pago: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    private MetodoPago mapearMetodoPago(ResultSet rs) throws SQLException {
        MetodoPago metodo = new MetodoPago();
        metodo.setIdMetodoPago(rs.getInt("id_metodo_pago"));
        metodo.setNombre(rs.getString("nombre"));
        metodo.setEstado(rs.getBoolean("activo"));
        return metodo;
    }
}