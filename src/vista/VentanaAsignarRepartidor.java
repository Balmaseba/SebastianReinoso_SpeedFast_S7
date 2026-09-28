package vista;

import controlador.GestorPedidos;
import modelo.Pedido;
import dao.PedidoDAO;
import dao.RepartidorDAO;
import modelo.Repartidor;
import dao.EntregaDAO;
import modelo.Entrega;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.time.LocalTime;
import java.time.LocalDate;

// Clase que representa la vntana de asignación de repartidores

public class VentanaAsignarRepartidor extends JFrame {

    // Controlador para gestionar los pedidos
    private GestorPedidos gestorPedidos;

    // DAO para gestionar los pedidos y repartidores
    private PedidoDAO pedidoDAO;
    private RepartidorDAO repartidorDAO;
    private EntregaDAO entregaDAO;

    // Componentes de la ventana
    private JComboBox<Pedido> cmbPedidos;
    private JComboBox<Repartidor> cmbRepartidores;
    private JButton btnAsignar;
    private JButton btnIniciar;
    private JButton btnActualizar;

    // Constructor
    public VentanaAsignarRepartidor(GestorPedidos gestorPedidos) {
        this.gestorPedidos = gestorPedidos;
        this.pedidoDAO = new PedidoDAO();
        this.repartidorDAO = new RepartidorDAO();
        this.entregaDAO = new EntregaDAO();

        // Configuración de la ventana
        setTitle("SpeedFast - Asignación de repartidores");
        setSize(500,300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear componentes
        cmbPedidos = new JComboBox<>();
        cmbRepartidores = new JComboBox<>();
        btnAsignar = new JButton("Asignar repartidor");
        btnIniciar = new JButton("Finalizar entrega");
        btnActualizar = new JButton("Actualizar pedidos");

        // Panel del formulario
        JPanel panelFormulario = new JPanel(new GridLayout(2,2, 10, 10));

        panelFormulario.add(new JLabel("Seleccionar pedido:"));
        panelFormulario.add(cmbPedidos);
        panelFormulario.add(new JLabel("Seleccionar repartidor:"));
        panelFormulario.add(cmbRepartidores);

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(3,1, 10, 10));

        panelBotones.add(btnAsignar);
        panelBotones.add(btnIniciar);
        panelBotones.add(btnActualizar);

        // Agregar componentes a la ventana
        add(panelFormulario, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.SOUTH);

        // Cargar los pedidos registrados
        actualizarPedidos();
        // Cargar los repartidores registrados
        actualizarRepartidores();

        // Eventos de los componentes
        btnAsignar.addActionListener(e -> asignarRepartidor());
        btnIniciar.addActionListener(e -> iniciarEntrega());
        btnActualizar.addActionListener(e -> {
            actualizarPedidos();
            actualizarRepartidores();
        });
    }

    // Método para actualizar los pedidos disponibles
    private void actualizarPedidos(){

        cmbPedidos.removeAllItems();

        // Recorrer los pedidos registrados
        for(Pedido pedido : pedidoDAO.listarTodos()){

            // Agregar solamente los pedidos pendientes o asignados
            if(pedido.getEstado().equals("Pendiente") || pedido.getEstado().equals("Asignado")){
                cmbPedidos.addItem(pedido);
            }
        }
    }

    // Método para actualizar los repartidores disponibles
    private void actualizarRepartidores(){
        cmbRepartidores.removeAllItems();

        // Recorrer los repartidores registrados en la base de datos
        for(Repartidor repartidor : repartidorDAO.listarTodos()){
            cmbRepartidores.addItem(repartidor);
        }
    }

    // Método para asignar un repartidor
    private void asignarRepartidor() {

        // Obtener el pedido seleccionado
        Pedido pedido = (Pedido) cmbPedidos.getSelectedItem();

        // Verificar que exista un pedido seleccionado
        if(pedido == null){
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un pedido");
            return;
        }

        // Obtener el repartidor seleccionado
        Repartidor repartidor = (Repartidor) cmbRepartidores.getSelectedItem();

        // Verificar que exista un repartidor seleccionado
        if(repartidor == null){
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un repartidor.");
            return;
        }

        // Verificar que el pedido esté pendiente
        if(!pedido.getEstado().equals("Pendiente")){
            JOptionPane.showMessageDialog(this,
                    "El pedido ya tiene un repartidor asignado.");
            return;
        }

        // Asignar el repartidor al pedido
        pedido.asignarRepartidor(repartidor.getNombre());

        // Actualizar el estado del pedido en la base de datos
        if(pedidoDAO.actualizarEstado(pedido)){

            // Crear la entrega con el pedido y repartidor seleccionados
            Entrega entrega = new Entrega(
                    pedido.getNumeroPedido(),
                    repartidor.getId(),
                    LocalDate.now(),
                    LocalTime.now()
            );

            // Guardar la entrega en la base de datos
            if(entregaDAO.guardar(entrega)){

                JOptionPane.showMessageDialog(this,
                        "Repartidor asignado correctamente.\n"
                                + "Pedido #" + pedido.getNumeroPedido() + "\n"
                                + "Repartidor: " + repartidor.getNombre() + "\n"
                                + "Estado: " + pedido.getEstado());

                // Actualizar los pedidos disponibles
                actualizarPedidos();

            } else {

                JOptionPane.showMessageDialog(this,
                        "No se pudo guardar la entrega en la base de datos.");
            }

        } else {

            JOptionPane.showMessageDialog(this,
                    "No se pudo actualizar el pedido en la base de datos.");
        }
    }

    // Método para finalizar la entrega

    private void iniciarEntrega() {

        Pedido pedido = (Pedido) cmbPedidos.getSelectedItem();

        // Verificar que exista un pedido seleccionado
        if (pedido == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un pedido.");
            return;
        }

        // Verificar que tenga un repartidor asignado
        if (!pedido.getEstado().equals("Asignado")) {
            JOptionPane.showMessageDialog(
                    this, "Debe asignar un repartidor antes de finalizar la entrega."
            );
            return;
        }

        // Despachar el pedido
        pedido.despachar();

        // Actualizar el estado en la base de datos
        PedidoDAO pedidoDAO = new PedidoDAO();

        if (pedidoDAO.actualizarEstado(pedido)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega finalizada correctamente.\n"
                            + "Pedido #" + pedido.getNumeroPedido() + "\n"
                            + "Estado: " + pedido.getEstado()
            );

            actualizarPedidos();

        } else {

            JOptionPane.showMessageDialog(
                    this, "No se pudo actualizar el estado en la base de datos."
            );
        }
    }
}

