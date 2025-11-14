package org.dao;

import org.empleado.modelo.Empleado;
import org.project.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmpleadoDAO {

    public List<Empleado> listar() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleado";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Empleado emp = new Empleado();
                emp.setIdEmpleado(rs.getInt("id_empleado"));
                emp.setNombre(rs.getString("nombre"));
                emp.setApellido(rs.getString("apellido"));
                emp.setUsuarioLogin(rs.getString("usuario_login"));
                emp.setContraseña(rs.getString("contraseña"));
                emp.setRol(rs.getString("rol"));
                empleados.add(emp);
            }

            System.out.println(" Empleados encontrados: " + empleados.size());

        } catch (SQLException e) {
            System.err.println(" Error al listar empleados: " + e.getMessage());
            e.printStackTrace();
        }

        return empleados;
    }

    public Optional<Empleado> obtenerPorId(int id) {
        String sql = "SELECT * FROM empleado WHERE id_empleado = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Empleado emp = new Empleado();
                emp.setIdEmpleado(rs.getInt("id_empleado"));
                emp.setNombre(rs.getString("nombre"));
                emp.setApellido(rs.getString("apellido"));
                emp.setUsuarioLogin(rs.getString("usuario_login"));
                emp.setContraseña(rs.getString("contraseña"));
                emp.setRol(rs.getString("rol"));
                return Optional.of(emp);
            }

        } catch (SQLException e) {
            System.err.println(" Error al obtener empleado: " + e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Empleado crear(Empleado e) {
        String sql = "INSERT INTO empleado (nombre, apellido, usuario_login, contraseña, rol) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, e.getNombre());
            stmt.setString(2, e.getApellido());
            stmt.setString(3, e.getUsuarioLogin());
            stmt.setString(4, e.getContraseña());
            stmt.setString(5, e.getRol());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                e.setIdEmpleado(rs.getInt(1));
            }

            System.out.println(" Empleado creado con ID: " + e.getIdEmpleado());

        } catch (SQLException ex) {
            System.err.println("Error al crear empleado: " + ex.getMessage());
            ex.printStackTrace();
        }

        return e;
    }

    public boolean actualizar(int id, Empleado e) {
        String sql = "UPDATE empleado SET nombre=?, apellido=?, usuario_login=?, contraseña=?, rol=? WHERE id_empleado=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getNombre());
            stmt.setString(2, e.getApellido());
            stmt.setString(3, e.getUsuarioLogin());
            stmt.setString(4, e.getContraseña());
            stmt.setString(5, e.getRol());
            stmt.setInt(6, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Empleado actualizado");
                return true;
            }

        } catch (SQLException ex) {
            System.err.println(" Error al actualizar empleado: " + ex.getMessage());
            ex.printStackTrace();
        }

        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM empleado WHERE id_empleado=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Empleado eliminado");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar empleado: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        System.out.println(" PROBANDO ");
        EmpleadoDAO dao = new EmpleadoDAO();

        System.out.println("Listando empleados:");
        List<Empleado> lista = dao.listar();

        System.out.println("Total encontrado: " + lista.size());

        if (lista.isEmpty()) {
            System.out.println("⚠La lista está vacia");
        } else {
            lista.forEach(e -> System.out.println("  - " + e));
        }
    }
    public Optional<Empleado> obtenerPorUsuario(String usuario_login) throws SQLException {
        String sql = "SELECT * FROM empleado WHERE usuario_login = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario_login);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setIdEmpleado(rs.getInt("id_empleado"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));
                empleado.setUsuarioLogin(rs.getString("usuario_login"));
                empleado.setContraseña(rs.getString("contraseña"));
                empleado.setRol(rs.getString("rol"));
                return Optional.of(empleado);
            }
        }
        return Optional.empty();
    }
}