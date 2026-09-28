package dao;

import modelo.Entrega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;

// Clase encargada de gestionar ls entregas en la base de datos

public class EntregaDAO {

    // Método para guardar una entrega en la base de datos

    public boolean guardar (Entrega entrega){
        String sql = "INSERT INTO entrega (id_pedido, id_repartidor, fecha, hora) VALUES (?,?,?,?)";

        Connection conexion = null;
        PreparedStatement statement = null;

        try {
            conexion = ConexionBD.conectar();
            statement = conexion.prepareStatement(sql);

            statement.setInt(1, entrega.getIdPedido());
            statement.setInt(2, entrega.getIdRepartidor());
            statement.setDate(3, Date.valueOf(entrega.getFecha()));
            statement.setTime(4, Time.valueOf(entrega.getHora()));

            statement.executeUpdate();

            System.out.println("Entrega guardada correctamente en la base de datos.");
            return true;

        } catch (SQLException e) {

            System.out.println("Error al guardar la entega.");
            System.out.println(e.getMessage());
            return false;

        } finally {
            try {
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión.");
                System.out.println(e.getMessage());
            }
        }
    }
}
