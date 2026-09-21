package modelo;

// Clase que representa un pedido de comida
// Utilizamos métodos y atributos de la clase Pedido (Herencia)

public class PedidoComida extends Pedido {

    // Constructor
    public PedidoComida(int numeroPedido, String direccionEntrega, double distanciaKm) {
        super(numeroPedido, direccionEntrega, distanciaKm);
    }

    // Sobrescritura para asignar repartidor automáticamente
    @Override
    public void asignarRepartidor() {

        System.out.println("Asignando repartidor...");

        super.asignarRepartidor("Ignacio Lagos");
    }

    // Sobrecarga del método para asignar un repartidor manualmente
    @Override
    public void asignarRepartidor(String nombreRepartidor) {

        super.asignarRepartidor(nombreRepartidor);
    }

    // Calculamos el tiempo de entrega
    @Override
    public int calcularTiempoEntrega() {

        return 15 + (int) (2 * getDistanciaKm());

    }

    @Override
    public String getTipoPedido() {
        return "Pedido Comida";
    }
}