package org.dao;

import org.project.db.DBConnection;
import org.empleado.modelo.Asistencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AsistenciaDAO {

    public List<Asistencia> listar() throws SQLException {
        List<Asistencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM asistencia";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Asistencia a = new Asistencia();
                a.setId_asistencia(rs.getInt("id_asistencia"));
                a.setId_repartidor(rs.getInt("id_repartidor"));
                a.setFecha(rs.getDate("fecha"));
                a.setHora_entrada(rs.getTime("hora_entrada"));
                a.setHora_salida(rs.getTime("hora_salida"));
                a.setEstado(rs.getString("estado"));
                a.setObservacion(rs.getString("observacion"));
                lista.add(a);
            }
        }
        return lista;
    }
    public Optional<Asistencia> obtenerPorId(int id) {
        String sql = "SELECT * FROM asistencia WHERE id_asistencia = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Asistencia asistencia = new Asistencia();
                asistencia.setId_asistencia(rs.getInt("id_asistencia"));
                asistencia.setId_repartidor(rs.getInt("id_repartidor"));
                asistencia.setFecha(rs.getDate("fecha"));
                asistencia.setHora_entrada(rs.getTime("hora_entrada"));
                asistencia.setHora_salida(rs.getTime("hora_salida"));
                asistencia.setEstado(rs.getString("estado"));
                asistencia.setObservacion(rs.getString("observacion"));
                return Optional.of(asistencia);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


    public Asistencia crear(Asistencia a) throws SQLException {
        String sql = "INSERT INTO asistencia (id_repartidor, fecha, hora_entrada, hora_salida, estado, justificacion) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, a.getId_repartidor());
            ps.setDate(2, a.getFecha());
            ps.setTime(3, a.getHora_entrada());
            ps.setTime(4, a.getHora_salida());
            ps.setString(5, a.getEstado());
            ps.setString(6, a.getObservacion());

            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) a.setId_asistencia(keys.getInt(1));
        }
        return a;
    }

    public boolean actualizar(int id, Asistencia a) throws SQLException {
        String sql = "UPDATE asistencia SET id_repartidor=?, fecha=?, hora_entrada=?, hora_salida=?, estado=?, observacion=? WHERE id_asistencia=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, a.getId_repartidor());
            ps.setDate(2, a.getFecha());
            ps.setTime(3, a.getHora_entrada());
            ps.setTime(4, a.getHora_salida());
            ps.setString(5, a.getEstado());
            ps.setString(6, a.getObservacion());
            ps.setInt(7, id);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM asistencia WHERE id_asistencia=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
