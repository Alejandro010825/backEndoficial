package org.dao;

import org.project.db.DBConnection;
import org.empleado.modelo.Destino;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DestinoDAO {

    public List<Destino> listar() throws SQLException {
        List<Destino> lista = new ArrayList<>();
        String sql = "SELECT * FROM destino";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Destino d = new Destino();
                d.setIdDestino(rs.getInt("id_destino"));
                d.setLugar(rs.getString("lugar"));
                d.setDireccion(rs.getString("direccion"));
                lista.add(d);
            }
        }
        return lista;
    }

    public Destino crear(Destino d) throws SQLException {
        String sql = "INSERT INTO destino (lugar, direccion) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, d.getLugar());
            ps.setString(2, d.getDireccion());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) d.setIdDestino(keys.getInt(1));
        }
        return d;
    }

    public boolean actualizar(int id, Destino d) throws SQLException {
        String sql = "UPDATE destino SET lugar=?, direccion=? WHERE id_destino=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getLugar());
            ps.setString(2, d.getDireccion());
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM destino WHERE id_destino=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
