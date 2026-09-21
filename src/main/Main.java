package main;
// Clase principal de nuestro programa
import modelo.Pedido;
import modelo.PedidoComida;
import modelo.PedidoEncomienda;
import modelo.PedidoExpress;
import vista.VentanaPrincipal;
import javax.swing.SwingUtilities;
/*
// Probamos los diferentes tipos de pedidos
public class Main {
    public static void main(String[] args) {

        //Creamos un pedido de comida
        PedidoComida comida = new PedidoComida(101,"Baldomero Lillo 15", 5, true);

        //Creamos un pedido de encomienda
        PedidoEncomienda encomienda = new PedidoEncomienda(102,"Av. Santa Rosa 567", 7,10,true);

        // Creamos un pedido express
        PedidoExpress express = new PedidoExpress(103, "Av.Jorge Alessandri 321", 8,true,true);

        //PARA PEDIDO COMIDA
        System.out.println("[modelo.Pedido Comida]");
        comida.reservarPedido();

        //Asignación automática
        comida.asignarRepartidor();
        comida.mostrarResumen();
        System.out.println("Tiempo estimado: " + comida.calcularTiempoEntrega() + " minutos.");
        comida.despachar();
        System.out.println("\n");

        //PARA PEDIDO ENCOMIENDA
        System.out.println("[modelo.Pedido Encomienda]");
        encomienda.reservarPedido();

        //Asignación masnual utilizando sobrecarga
        encomienda.asignarRepartidor("Daniela Tapia");
        encomienda.mostrarResumen();
        System.out.println("Tiempo estimado: " + encomienda.calcularTiempoEntrega() + " minutos.");
        encomienda.despachar();
        System.out.println("\n");

        //PARA PEDIDO EXPRESS
        System.out.println("[modelo.Pedido Express]");
        express.reservarPedido();
        express.asignarRepartidor();
        express.mostrarResumen();
        System.out.println("Tiempo estimado: " + express.calcularTiempoEntrega() + " minutos.");
        express.cancelar();
        System.out.println("\n");

        //Mostramos el historial de pedidos entregados
    }
}
*/

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });

    }
}
