package org.project.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/BACK?serverTimezone=UTC&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL cargado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR");
            System.err.println("Verifica que mysql-connector-j esté en las dependencias");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        System.out.println(" Intentando conectar a: " + URL);
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(" Conexion exitosa a la base de datos BACK");
            return conn;
        } catch (SQLException e) {
            System.err.println(" Error al conectar:");
            System.err.println("   Mensaje: " + e.getMessage());
            System.err.println("   Código: " + e.getErrorCode());
            throw e;
        }
    }

    public static void main(String[] args) {
        System.out.println("Probando conexion a la base de datos...");
        try (Connection conn = getConnection()) {
            System.out.println(" Prueba exitosa");
        } catch (SQLException e) {
            System.err.println("Prueba fallida");
        }
    }
}