package org.dao;

import org.empleado.modelo.Pago;
import org.project.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PagoDAO {

    public List<Pago> listar() {
        List<Pago> pagos = new ArrayList<>();
        String sql = "SELECT * FROM pago";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pago pago = mapearPago(rs);
                pagos.add(pago);
            }

            System.out.println("Pagos encontrados: " + pagos.size());

        } catch (SQLException e) {
            System.err.println("Error al listar pagos: " + e.getMessage());
            e.printStackTrace();
        }

        return pagos;
    }

    public Optional<Pago> obtenerPorId(int id) {
        String sql = "SELECT * FROM pago WHERE id_pago = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearPago(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener pago: " + e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public List<Pago> obtenerPorRepartidor(int idRepartidor) {
        List<Pago> pagos = new ArrayList<>();
        String sql = "SELECT * FROM pago WHERE id_repartidor = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRepartidor);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pagos.add(mapearPago(rs));
            }

            System.out.println("Pagos encontrados para repartidor " + idRepartidor + ": " + pagos.size());

        } catch (SQLException e) {
            System.err.println("Error al obtener pagos por repartidor: " + e.getMessage());
            e.printStackTrace();
        }

        return pagos;
    }

    public Pago crear(Pago pago) {
        String sql = "INSERT INTO pago (id_repartidor, periodo_inicio, periodo_fin, fecha_generado, " +
                "sueldo_base, viajes_hechos, sueldo_viajes, descuentos, total_neto) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pago.getIdRepartidor());
            stmt.setDate(2, pago.getPeriodoInicio());
            stmt.setDate(3, pago.getPeriodoFin());
            stmt.setTimestamp(4, pago.getFechaGenerado());
            stmt.setBigDecimal(5, pago.getSueldoBase());
            stmt.setInt(6, pago.getViajesHechos());
            stmt.setBigDecimal(7, pago.getSueldoViajes());
            stmt.setBigDecimal(8, pago.getDescuentos());
            stmt.setBigDecimal(9, pago.getTotalNeto());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                pago.setIdPago(rs.getInt(1));
            }

            System.out.println("Pago creado con ID: " + pago.getIdPago());

        } catch (SQLException e) {
            System.err.println("Error al crear pago: " + e.getMessage());
            e.printStackTrace();
        }

        return pago;
    }

    public boolean actualizar(int id, Pago pago) {
        String sql = "UPDATE pago SET id_repartidor=?, periodo_inicio=?, periodo_fin=?, " +
                "fecha_generado=?, sueldo_base=?, viajes_hechos=?, sueldo_viajes=?, " +
                "descuentos=?, total_neto=? WHERE id_pago=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pago.getIdRepartidor());
            stmt.setDate(2, pago.getPeriodoInicio());
            stmt.setDate(3, pago.getPeriodoFin());
            stmt.setTimestamp(4, pago.getFechaGenerado());
            stmt.setBigDecimal(5, pago.getSueldoBase());
            stmt.setInt(6, pago.getViajesHechos());
            stmt.setBigDecimal(7, pago.getSueldoViajes());
            stmt.setBigDecimal(8, pago.getDescuentos());
            stmt.setBigDecimal(9, pago.getTotalNeto());
            stmt.setInt(10, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Pago actualizado");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar pago: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM pago WHERE id_pago=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Pago eliminado");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar pago: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    private Pago mapearPago(ResultSet rs) throws SQLException {
        Pago pago = new Pago();
        pago.setIdPago(rs.getInt("id_pago"));
        pago.setIdRepartidor(rs.getInt("id_repartidor"));
        pago.setPeriodoInicio(rs.getDate("periodo_inicio"));
        pago.setPeriodoFin(rs.getDate("periodo_fin"));
        pago.setFechaGenerado(rs.getTimestamp("fecha_generado"));
        pago.setSueldoBase(rs.getBigDecimal("sueldo_base"));
        pago.setViajesHechos(rs.getInt("viajes_hechos"));
        pago.setSueldoViajes(rs.getBigDecimal("sueldo_viajes"));
        pago.setDescuentos(rs.getBigDecimal("descuentos"));
        pago.setTotalNeto(rs.getBigDecimal("total_neto"));
        return pago;
    }
}