package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Clase encargada de gestionar la conexión con la base de datos

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/speedfast_db";
    private static final String USER = "root";
    private static final String PASSWORD = "dalezeldadale";

    // Método para establecer la conexión con la base de datos

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
