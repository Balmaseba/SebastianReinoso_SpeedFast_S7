package dao;

import modelo.Pedido;
import modelo.PedidoEncomienda;
import modelo.PedidoComida;
import modelo.PedidoExpress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

// Clase encargada de gestionar los pedidos en la base de datos

public class PedidoDAO {

    // Método para guardar un pedido en la base de datos

    public boolean guardar(Pedido pedido) {

        String sql = "INSERT INTO pedido (direccion, tipo, estado, distancia, peso) VALUES (?, ?, ?, ?, ?)";

        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultado = null;

        try {
            conexion = ConexionBD.conectar();

            // Preparar la consulta y solicitar el ID generado
            statement = conexion.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, pedido.getDireccionEntrega());
            statement.setString(2, obtenerTipoPedido(pedido));
            statement.setString(3, obtenerEstadoPedido(pedido));
            statement.setDouble(4, pedido.getDistanciaKm());

            // Guardar peso solo si el pedido es encomienda
            if (pedido instanceof PedidoEncomienda) {

                PedidoEncomienda encomienda = (PedidoEncomienda) pedido;
                statement.setDouble(5, encomienda.getPeso());

            } else {

                statement.setNull(5, Types.DOUBLE);
            }

            statement.executeUpdate();

            // Obtener el ID generado por la base de datos
            resultado = statement.getGeneratedKeys();

            if (resultado.next()) {

                int idGenerado = resultado.getInt(1);

                // Asignar el ID generado al pedido
                pedido.setNumeroPedido(idGenerado);
            }

            System.out.println("Pedido guardado correctamente en la base de datos");
            System.out.println("ID Generado: " + pedido.getNumeroPedido());

            return true;

        } catch (SQLException e) {

            System.out.println("Error al guardar el pedido");
            System.out.println(e.getMessage());

            return false;

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

                System.out.println("Error al cerrar la conexión");
                System.out.println(e.getMessage());
            }
        }
    }

    // Método para actualizar el estado de un pedido en la base de datos

    public boolean actualizarEstado(Pedido pedido) {

        String sql = "UPDATE pedido SET estado = ? WHERE id = ?";

        Connection conexion = null;
        PreparedStatement statement = null;

        try {

            conexion = ConexionBD.conectar();
            statement = conexion.prepareStatement(sql);

            statement.setString(1, obtenerEstadoPedido(pedido));
            statement.setInt(2, pedido.getNumeroPedido());

            int filasActualizadas = statement.executeUpdate();

            if (filasActualizadas > 0) {

                System.out.println("Pedido actualizado correctamente");
                return true;
            }

            return false;

        } catch (SQLException e) {

            System.out.println("Error al actualizar el estado del pedido");
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

                System.out.println("Error al cerrar la conexion");
                System.out.println(e.getMessage());
            }
        }
    }

    // Método para listar todos los pedidos de la base de datos

    public List<Pedido> listarTodos() {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = """
                SELECT p.id,
                       p.direccion,
                       p.tipo,
                       p.estado,
                       p.distancia,
                       p.peso,
                       r.nombre AS repartidor
                FROM pedido p
                LEFT JOIN entrega e ON e.id = (
                    SELECT MAX(e2.id)
                    FROM entrega e2
                    WHERE e2.id_pedido = p.id
                )
                LEFT JOIN repartidor r ON e.id_repartidor = r.id
                ORDER BY p.id
                """;

        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultado = null;

        try {

            conexion = ConexionBD.conectar();
            statement = conexion.prepareStatement(sql);

            resultado = statement.executeQuery();

            while (resultado.next()) {

                // Obtener datos desde la base de datos
                int id = resultado.getInt("id");
                String direccion = resultado.getString("direccion");
                String tipo = resultado.getString("tipo");
                double distancia = resultado.getDouble("distancia");
                String estado = resultado.getString("estado");
                String repartidor = resultado.getString("repartidor");

                Pedido pedido;

                // Crear el pedido según el tipo almacenado
                if (tipo.equals("COMIDA")) {

                    pedido = new PedidoComida(
                            id,
                            direccion,
                            distancia
                    );

                } else if (tipo.equals("ENCOMIENDA")) {

                    double peso = resultado.getDouble("peso");

                    pedido = new PedidoEncomienda(
                            id,
                            direccion,
                            distancia,
                            peso
                    );

                } else {

                    pedido = new PedidoExpress(
                            id,
                            direccion,
                            distancia
                    );
                }

                // Asignar el estado almacenado en la base de datos
                if (estado.equals("PENDIENTE")) {

                    pedido.setEstado("Pendiente");

                } else if (estado.equals("EN_REPARTO")) {

                    pedido.setEstado("Asignado");

                    // Recuperar también el repartidor asignado
                    if (repartidor != null) {
                        pedido.setRepartidorAsignado(repartidor);
                    }

                } else if (estado.equals("ENTREGADO")) {

                    pedido.setEstado("Entregado");

                    // Recuperar también el repartidor que realizó la entrega
                    if (repartidor != null) {
                        pedido.setRepartidorAsignado(repartidor);
                    }
                }

                // Agregar el pedido a la lista
                pedidos.add(pedido);
            }

        } catch (SQLException e) {

            System.out.println("Error al listar los pedidos");
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

                System.out.println("Error al cerrar la conexion");
                System.out.println(e.getMessage());
            }
        }

        return pedidos;
    }

    // Método para obtener el tipo de pedido para la base de datos

    private String obtenerTipoPedido(Pedido pedido) {

        if (pedido.getTipoPedido().equals("Pedido Comida")) {
            return "COMIDA";
        }

        if (pedido.getTipoPedido().equals("Pedido Encomienda")) {
            return "ENCOMIENDA";
        }

        if (pedido.getTipoPedido().equals("Pedido Express")) {
            return "EXPRESS";
        }

        return "DESCONOCIDO";
    }

    // Método para obtener el estado del pedido para la base de datos

    private String obtenerEstadoPedido(Pedido pedido) {

        if (pedido.getEstado().equals("Pendiente")) {
            return "PENDIENTE";
        }

        if (pedido.getEstado().equals("Asignado")) {
            return "EN_REPARTO";
        }

        if (pedido.getEstado().equals("Entregado")) {
            return "ENTREGADO";
        }

        return pedido.getEstado().toUpperCase();
    }
}

