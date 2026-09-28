package modelo;

// Clase que representa un repartidor

public class Repartidor {

    private int id;
    private String nombre;

    // Constructor

    public Repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Constructor para registrar nuevo repartidor

    public Repartidor(String nombre) {
        this.nombre = nombre;
    }

    // Getter necesarios

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    // Método para mostrar el repartidor

    @Override
    public String toString(){
        return nombre;
    }
}
