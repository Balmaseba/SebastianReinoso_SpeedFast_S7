package modelo;

import java.util.ArrayList;
import interfaces.Despachable;
import interfaces.Cancelable;
import interfaces.Rastreable;

// Clase abstracta que representa un pedido

public abstract class Pedido implements Despachable, Cancelable, Rastreable {

    private int numeroPedido;
    private String direccionEntrega;
    private double distanciaKm;
    private String repartidorAsignado;
    private boolean cancelado;
    private String estado;

    // ArrayList para almacenar el historial de pedidos entregados
    private static ArrayList<String> historial = new ArrayList<>();

    // Constructor

    public Pedido(int numeroPedido, String direccionEntrega, double distanciaKm) {
        this.numeroPedido = numeroPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
        this.repartidorAsignado = "Sin asignar";
        this.cancelado = false;
        this.estado = "Pendiente";
    }

    // Getter y setter necesarios

    public int getNumeroPedido() {
        return numeroPedido;
    }
    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setRepartidorAsignado(String repartidorAsignado) {
        this.repartidorAsignado = repartidorAsignado;
    }

    public String getRepartidorAsignado() {
        return repartidorAsignado;
    }
    public String getDireccionEntrega() {
        return direccionEntrega;
    }
    public boolean isCancelado() {
        return cancelado;
    }
    public String getEstado(){
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Método para mostrar información

    public void mostrarResumen(){
        System.out.println("modelo.Pedido #: " + this.numeroPedido + "\n"
                            + "Dirección: " +  this.direccionEntrega + "\n"
                            + "Distancia: " +  this.distanciaKm + "km\n"
                            + "Repartidor Asignado: " +   this.repartidorAsignado + "\n");
    }

    // Método para reservar el pedido

    public void reservarPedido(){
        System.out.println("Pedido # " + numeroPedido + " reservado correctamente.");
    }

    // Método implementado desde la interface interfaces.Despachable

    @Override

    public void despachar(){
        if(cancelado){
            System.out.println("El pedido fue cancelado.");
        }else if(estado.equals("Entregado")){
            System.out.println("El pedido ya fue entregado");
        }else if(repartidorAsignado.equals("Sin asignar")) {
            System.out.println("Sin repartidor... El pedido no puede ser despachado.");
        }else{
            estado = "Entregado";
            System.out.println("Pedido despachado correctamente.");
            historial.add("- " + getTipoPedido() + " #" + numeroPedido + " - entregado por " + repartidorAsignado);
        }
    }

    // Método implementado desde la interface interfaces.Cancelable

    @Override
    public void cancelar(){
        if(estado.equals("Entregado")){
            System.out.println("No se puede cancelar un pedido entregado.");
            return;
        }
        cancelado = true;
        estado = "Cancelado";

        System.out.println("-> Pedido cancelado exitosamente.");
    }

    // Método implementado desde la interface interfaces.Rastreable;
    @Override
    public void verHistorial(){
        System.out.println("Historial de pedidos: ");
        if(historial.isEmpty()){
            System.out.println("No existen pedidos entregados.");
        }else{
            for(String pedido : historial){
                System.out.println(pedido);
            }
        }
    }

    // Método para identificar el tipo de pedido
    public abstract String getTipoPedido();

    // Método que las clases hijas sobrescriben
    public abstract void asignarRepartidor();

    // Método sobrecargado para asignar manualmente
    public void asignarRepartidor(String nombreRepartidor){

        // Verificar si el pedido está cancelado o entregado
        if(cancelado || estado.equals("Entregado")){
            System.out.println("El pedido no puede ser asignado.");
            return;
        }
        // Validar el nombre del repartidor
        if(nombreRepartidor == null || nombreRepartidor.trim().isEmpty()){
            System.out.println("Debe ingresar un nombre válido.");
            return;
        }

        this.repartidorAsignado = nombreRepartidor.trim();
        this.estado = "Asignado";
        System.out.println("-> Pedido asignado a " + repartidorAsignado);
    }

    // Método abstracto para calcular teimpo de entrega
    public abstract int calcularTiempoEntrega();

    // Método para mostrar el pedido en el JComboBox
    @Override
    public String toString() {

        return "Pedido #" + numeroPedido
                + " - " + getTipoPedido()
                + " - " + estado;

    }
}
