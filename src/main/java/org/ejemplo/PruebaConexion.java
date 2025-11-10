package org.ejemplo;

import org.project.db.DBConnection;
import java.sql.Connection;

public class PruebaConexion {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println(" Conexion a MySQL local!");
        } catch (Exception e) {
            System.out.println(" Error de conexion: " + e.getMessage());
        }
    }
}
