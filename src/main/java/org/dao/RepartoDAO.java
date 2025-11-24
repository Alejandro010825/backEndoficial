package org.dao;

import org.empleado.modelo.Reparto;
import org.project.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.empleado.modelo.RepartoGrafica;

public class RepartoDAO {

    public List<Reparto> listar() {
        List<Reparto> repartos = new ArrayList<>();
        String sql = "SELECT * FROM reparto";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Reparto reparto = mapearReparto(rs);
                repartos.add(reparto);
            }

            System.out.println("Repartos encontrados: " + repartos.size());

        } catch (SQLException e) {
            System.err.println("Error al listar repartos: " + e.getMessage());
            e.printStackTrace();
        }

        return repartos;
    }

    public Optional<Reparto> obtenerPorId(int id) {
        String sql = "SELECT * FROM reparto WHERE id_reparto = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearReparto(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener reparto: " + e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Reparto crear(Reparto reparto) {
        String sql = "INSERT INTO reparto (ticket, monto_total, monto_pagado, fecha_creacion, " +
                "hora_salida, hora_llegada, estado, cantidad_repartos, id_vendedor, " +
                "id_repartidor, id_destino, id_metodo_pago) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, reparto.getTicket());
            stmt.setBigDecimal(2, reparto.getMontoTotal());
            stmt.setBigDecimal(3, reparto.getMontoPagado());
            stmt.setDate(4, reparto.getFechaCreacion());
            stmt.setTime(5, reparto.getHoraSalida());
            stmt.setTime(6, reparto.getHoraLlegada());
            stmt.setString(7, reparto.getEstado());
            stmt.setInt(8, reparto.getCantidad_repartos());

            if (reparto.getIdVendedor() != null) {
                stmt.setInt(9, reparto.getIdVendedor());
            } else {
                stmt.setNull(9, Types.INTEGER);
            }

            if (reparto.getIdRepartidor() != null) {
                stmt.setInt(10, reparto.getIdRepartidor());
            } else {
                stmt.setNull(10, Types.INTEGER);
            }

            if (reparto.getIdDestino() != null) {
                stmt.setInt(11, reparto.getIdDestino());
            } else {
                stmt.setNull(11, Types.INTEGER);
            }

            if (reparto.getIdMetodoPago() != null) {
                stmt.setInt(12, reparto.getIdMetodoPago());
            } else {
                stmt.setNull(12, Types.INTEGER);
            }

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                reparto.setIdReparto(rs.getInt(1));
            }

            System.out.println("Reparto creado con ID: " + reparto.getIdReparto());

        } catch (SQLException e) {
            System.err.println("Error al crear reparto: " + e.getMessage());
            e.printStackTrace();
        }

        return reparto;
    }

    public boolean actualizar(int id, Reparto reparto) {
        String sql = "UPDATE reparto SET ticket=?, monto_total=?, monto_pagado=?, fecha_creacion=?, " +
                "hora_salida=?, hora_llegada=?, estado=?, cantidad_repartos=?, id_vendedor=?, " +
                "id_repartidor=?, id_destino=?, id_metodo_pago=? WHERE id_reparto=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, reparto.getTicket());
            stmt.setBigDecimal(2, reparto.getMontoTotal());
            stmt.setBigDecimal(3, reparto.getMontoPagado());
            stmt.setDate(4, reparto.getFechaCreacion());
            stmt.setTime(5, reparto.getHoraSalida());
            stmt.setTime(6, reparto.getHoraLlegada());
            stmt.setString(7, reparto.getEstado());
            stmt.setInt(8, reparto.getCantidad_repartos());

            if (reparto.getIdVendedor() != null) {
                stmt.setInt(9, reparto.getIdVendedor());
            } else {
                stmt.setNull(9, Types.INTEGER);
            }

            if (reparto.getIdRepartidor() != null) {
                stmt.setInt(10, reparto.getIdRepartidor());
            } else {
                stmt.setNull(10, Types.INTEGER);
            }

            if (reparto.getIdDestino() != null) {
                stmt.setInt(11, reparto.getIdDestino());
            } else {
                stmt.setNull(11, Types.INTEGER);
            }

            if (reparto.getIdMetodoPago() != null) {
                stmt.setInt(12, reparto.getIdMetodoPago());
            } else {
                stmt.setNull(12, Types.INTEGER);
            }

            stmt.setInt(13, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Reparto actualizado");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar reparto: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM reparto WHERE id_reparto=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Reparto eliminado");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar reparto: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    private Reparto mapearReparto(ResultSet rs) throws SQLException {
        Reparto reparto = new Reparto();
        reparto.setIdReparto(rs.getInt("id_reparto"));
        reparto.setTicket(rs.getString("ticket"));
        reparto.setMontoTotal(rs.getBigDecimal("monto_total"));
        reparto.setMontoPagado(rs.getBigDecimal("monto_pagado"));
        reparto.setFechaCreacion(rs.getDate("fecha_creacion"));
        reparto.setHoraSalida(rs.getTime("hora_salida"));
        reparto.setHoraLlegada(rs.getTime("hora_llegada"));
        reparto.setEstado(rs.getString("estado"));
        reparto.setCantidad_repartos(rs.getInt("cantidad_repartos"));

        int idVendedor = rs.getInt("id_vendedor");
        if (!rs.wasNull()) reparto.setIdVendedor(idVendedor);

        int idRepartidor = rs.getInt("id_repartidor");
        if (!rs.wasNull()) reparto.setIdRepartidor(idRepartidor);

        int idDestino = rs.getInt("id_destino");
        if (!rs.wasNull()) reparto.setIdDestino(idDestino);

        int idMetodoPago = rs.getInt("id_metodo_pago");
        if (!rs.wasNull()) reparto.setIdMetodoPago(idMetodoPago);

        return reparto;
    }

    public List<RepartoGrafica> obtenerRepartosPorFecha(int idRepartidor, Date fechaInicio, Date fechaFin) throws SQLException {
        List<RepartoGrafica> datos = new ArrayList<>();
        String sql = "SELECT fecha_creacion, COUNT(*) as cantidad " +
                "FROM reparto " +
                "WHERE id_repartidor = ? " +
                "AND fecha_creacion BETWEEN ? AND ? " +
                "GROUP BY fecha_creacion " +
                "ORDER BY fecha_creacion ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idRepartidor);
            ps.setDate(2, fechaInicio);
            ps.setDate(3, fechaFin);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                RepartoGrafica dato = new RepartoGrafica();
                dato.setFecha(rs.getDate("fecha_creacion"));
                dato.setCantidadRepartos(rs.getInt("cantidad"));
                dato.setIdRepartidor(idRepartidor);
                datos.add(dato);
            }

            System.out.println(" Datos para gráfica obtenidos: " + datos.size());
        }
        return datos;
    }

    public List<RepartoGrafica> obtenerRepartosPorFecha(Date fechaInicio, Date fechaFin) throws SQLException {
        List<RepartoGrafica> datos = new ArrayList<>();
        String sql = "SELECT fecha_creacion, COUNT(*) as cantidad " +
                "FROM reparto " +
                "WHERE fecha_creacion BETWEEN ? AND ? " +
                "GROUP BY fecha_creacion " +
                "ORDER BY fecha_creacion ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, fechaInicio);
            ps.setDate(2, fechaFin);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                RepartoGrafica dato = new RepartoGrafica();
                dato.setFecha(rs.getDate("fecha_creacion"));
                dato.setCantidadRepartos(rs.getInt("cantidad"));
                datos.add(dato);
            }

            System.out.println(" Datos para gráfica obtenidos: " + datos.size());
        }
        return datos;
    }
}