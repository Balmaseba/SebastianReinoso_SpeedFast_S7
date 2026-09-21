package modelo;

// Clase que representa un pedido Express
// Utilizamos métodos y atributos de la clase Pedido (Herencia)

public class PedidoExpress extends Pedido {

    // Constructor
    public PedidoExpress(int numeroPedido, String direccionEntrega, double distanciaKm) {

        super(numeroPedido, direccionEntrega, distanciaKm);
    }

    // Sobrescritura para asignar repartidor automáticamente
    @Override
    public void asignarRepartidor() {

        System.out.println("Asignando repartidor...");

        super.asignarRepartidor("Carlos Soto");
    }

    // Sobrecarga del método para asignar un repartidor manualmente
    @Override
    public void asignarRepartidor(String nombreRepartidor) {

        super.asignarRepartidor(nombreRepartidor);
    }

    // Calculamos el tiempo del pedido Express
    @Override
    public int calcularTiempoEntrega() {

        int tiempoEntrega = 10;

        // Si la distancia supera los 5 km, se agregan 5 minutos
        if (getDistanciaKm() > 5) {
            tiempoEntrega += 5;
        }

        return tiempoEntrega;
    }

    @Override
    public String getTipoPedido() {
        return "Pedido Express";
    }
}
