package modelo;

// Clase que representa un pedido de encomienda
// Utilizamos métodos y atributos de la clase Pedido (Herencia)

public class PedidoEncomienda extends Pedido {

    private double peso;

    // Constructor
    public PedidoEncomienda(int numeroPedido, String direccionEntrega,
                            double distanciaKm, double peso) {

        super(numeroPedido, direccionEntrega, distanciaKm);
        this.peso = peso;
    }

    // Getter necesarios
    public double getPeso() {
        return peso;
    }

    // Sobrescritura para asignar repartidor automáticamente
    @Override
    public void asignarRepartidor() {

        System.out.println("Asignando repartidor...");

        if (peso > 0) {

            super.asignarRepartidor("Omar Ortega");

        } else {

            System.out.println("El peso de la encomienda no es válido.");

        }
    }

    // Sobrecarga del método para asignar un repartidor manualmente
    @Override
    public void asignarRepartidor(String nombreRepartidor) {

        if (peso > 0) {

            super.asignarRepartidor(nombreRepartidor);

        } else {

            System.out.println("El peso de la encomienda no es válido.");

        }
    }

    // Calculamos el tiempo de entrega para encomiendas
    @Override
    public int calcularTiempoEntrega() {

        return (int) Math.round(20 + (1.5 * getDistanciaKm()));

    }

    @Override
    public String getTipoPedido() {
        return "Pedido Encomienda";
    }
}
