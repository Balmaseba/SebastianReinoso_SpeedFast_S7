package controlador;

import java.util.ArrayList;
import modelo.Pedido;
import modelo.PedidoExpress;

// Clase para gestionar los pedidos del sistema

public class GestorPedidos {

    //ArrayList para almacenar los pedidos registrados
    private ArrayList<Pedido> pedidos;

    // Constructor
    public GestorPedidos(){
        pedidos = new ArrayList<>();
    }

    // Método para registrar un pedido
    public boolean registrarPedido(Pedido pedido){
        if(pedido == null) {
            return false;
        }

        // Verificar que el número de pedido no esté registrado
        if (buscarPedido(pedido.getNumeroPedido()) != null){
            return false;
        }
        pedidos.add(pedido);
        return true;
    }

    // Método para buscar un pedido por su número

    public Pedido buscarPedido(int numeroPedido) {
        for (Pedido pedido : pedidos) {
            if (pedido.getNumeroPedido() == numeroPedido){
                return pedido;
            }
        }
        return null;
    }

    // Método para obtener los pedidos registrados
    public ArrayList<Pedido> getPedidos(){
        return new ArrayList<>(pedidos);
    }

    // Método para obtener la cantidad de pedidos
    public int getCantidadPedidos(){
        return pedidos.size();
    }
}


