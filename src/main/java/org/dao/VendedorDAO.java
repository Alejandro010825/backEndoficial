package org.dao;

import org.empleado.modelo.Vendedor;
import org.project.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VendedorDAO {

    public List<Vendedor> listar() {
        List<Vendedor> vendedores = new ArrayList<>();
        String sql = "SELECT * FROM vendedor";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vendedor vendedor = mapearVendedor(rs);
                vendedores.add(vendedor);
            }

            System.out.println("Vendedores encontrados: " + vendedores.size());

        } catch (SQLException e) {
            System.err.println("Error al listar vendedores: " + e.getMessage());
            e.printStackTrace();
        }

        return vendedores;
    }

    public Optional<Vendedor> obtenerPorId(int id) {
        String sql = "SELECT * FROM vendedor WHERE id_vendedor = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearVendedor(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener vendedor: " + e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Vendedor crear(Vendedor vendedor) {
        String sql = "INSERT INTO vendedor (nombre) VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, vendedor.getNombre());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                vendedor.setId_vendedor(rs.getInt(1));
            }

            System.out.println("Vendedor creado con ID: " + vendedor.getId_vendedor());

        } catch (SQLException e) {
            System.err.println("Error al crear vendedor: " + e.getMessage());
            e.printStackTrace();
        }

        return vendedor;
    }

    public boolean actualizar(int id, Vendedor vendedor) {
        String sql = "UPDATE vendedor SET nombre=? WHERE id_vendedor=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vendedor.getNombre());
            stmt.setInt(2, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Vendedor actualizado");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar vendedor: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM vendedor WHERE id_vendedor=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Vendedor eliminado");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar vendedor: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    private Vendedor mapearVendedor(ResultSet rs) throws SQLException {
        Vendedor vendedor = new Vendedor();
        vendedor.setId_vendedor(rs.getInt("id_vendedor"));
        vendedor.setNombre(rs.getString("nombre"));
        return vendedor;
    }
}