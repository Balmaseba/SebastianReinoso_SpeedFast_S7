package dao;

import modelo.Repartidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Clase encargada de gestionar los repartidores en la base de datos

public class RepartidorDAO {

    // Método para guardar un repartidor en la base de datos

    public boolean guardar(Repartidor repartidor) {

        String sql = "INSERT INTO repartidor (nombre) VALUES (?)";

        Connection conexion = null;
        PreparedStatement statement = null;

        try {

            conexion = ConexionBD.conectar();
            statement = conexion.prepareStatement(sql);

            statement.setString(1, repartidor.getNombre());

            int filasInsertadas = statement.executeUpdate();

            if (filasInsertadas > 0) {
                System.out.println(
                        "Repartidor guardado correctamente en la base de datos."
                );
                return true;
            }

            return false;

        } catch (SQLException e) {

            System.out.println("Error al guardar el repartidor");
            System.out.println(e.getMessage());

            return false;

        } finally {

            try {

                if (statement != null) {
                    statement.close();
                }

                if (conexion != null) {
                    conexion.close();
                }

            } catch (SQLException e) {

                System.out.println("Error al cerrar la conexión.");
                System.out.println(e.getMessage());
            }
        }
    }

    // Método para listar todos los repartidores de la base de datos

    public List<Repartidor> listarTodos() {

        List<Repartidor> repartidores = new ArrayList<>();

        String sql = "SELECT id, nombre FROM repartidor";

        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultado = null;

        try {

            conexion = ConexionBD.conectar();
            statement = conexion.prepareStatement(sql);

            resultado = statement.executeQuery();

            while (resultado.next()) {

                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");

                Repartidor repartidor = new Repartidor(id, nombre);

                repartidores.add(repartidor);
            }

        } catch (SQLException e) {

            System.out.println("Error al listar los repartidores");
            System.out.println(e.getMessage());

        } finally {

            try {

                if (resultado != null) {
                    resultado.close();
                }

                if (statement != null) {
                    statement.close();
                }

                if (conexion != null) {
                    conexion.close();
                }

            } catch (SQLException e) {

                System.out.println("Error al cerrar la conexion.");
                System.out.println(e.getMessage());
            }
        }

        return repartidores;
    }
}