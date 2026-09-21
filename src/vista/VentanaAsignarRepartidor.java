package vista;

import controlador.GestorPedidos;
import modelo.Pedido;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFrame;

// Clase que representa la vntana de asignación de repartidores

public class VentanaAsignarRepartidor extends JFrame {

    // Controlador para gestionar los pedidos
    private GestorPedidos gestorPedidos;

    // Componentes de la ventana
    private JComboBox<Pedido> cmbPedidos;
    private JTextField txtRepartidor;
    private JButton btnAsignar;
    private JButton btnIniciar;
    private JButton btnActualizar;

    // Constructor
    public VentanaAsignarRepartidor(GestorPedidos gestorPedidos) {
        this.gestorPedidos = gestorPedidos;

        // Configuración de la ventana
        setTitle("SpeedFast - Asignación de repartidores");
        setSize(500,300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear componentes
        cmbPedidos = new JComboBox<>();
        txtRepartidor = new JTextField();
        btnAsignar = new JButton("Asignar repartidor");
        btnIniciar = new JButton("Iniciar entrega");
        btnActualizar = new JButton("Actualizar pedidos");

        // Panel del formulario
        JPanel panelFormulario = new JPanel(new GridLayout(2,2, 10, 10));

        panelFormulario.add(new JLabel("Seleccionar pedido:"));
        panelFormulario.add(cmbPedidos);
        panelFormulario.add(new JLabel("Nombre del repartidor:"));
        panelFormulario.add(txtRepartidor);

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

        // Eventos de los componentes
        btnAsignar.addActionListener(e -> asignarRepartidor());
        btnIniciar.addActionListener(e -> iniciarEntrega());
        btnActualizar.addActionListener(e -> actualizarPedidos());
    }

    // Método para actualizar los pedidos disponibles
    private void actualizarPedidos(){

        cmbPedidos.removeAllItems();

        // Recorrer los pedidos registrados
        for(Pedido pedido : gestorPedidos.getPedidos()){

            // Agregar solamente los pedidos pendientes o asignados
            if(pedido.getEstado().equals("Pendiente") || pedido.getEstado().equals("Asignado")){
                cmbPedidos.addItem(pedido);
            }
        }
    }

    // Método para asignar un repartidor
    private void asignarRepartidor() {
        Pedido pedido = (Pedido) cmbPedidos.getSelectedItem();
        // Verificar que exista un pedido seleccionado
        if(pedido == null){
            JOptionPane.showMessageDialog(this,"Debe seleccionar un pedido");
            return;
        }
        // Validar el nombre del repartidor
        String nombre = txtRepartidor.getText().trim();

        if (nombre.isEmpty()){
            JOptionPane.showMessageDialog(this,"Debe ingresar el nombre del repartidor.");
            return;
        }
        // Verificar que el pedido esté pendiente
        if(!pedido.getEstado().equals("Pendiente")){
            JOptionPane.showMessageDialog(this,"El pedido ya tiene un repartidor asignado");
            return;
        }
        // Asignar el repartidor del pedido
        pedido.asignarRepartidor(nombre);

        JOptionPane.showMessageDialog(this,"Asignación realizada... Estado: " + pedido.getEstado());
        actualizarPedidos();
    }

    // Método para iniciar la entrega
    private void iniciarEntrega() {

        Pedido pedido = (Pedido) cmbPedidos.getSelectedItem();

        // Verificar que exista un pedido seleccionado
        if(pedido == null){
            JOptionPane.showMessageDialog(this,"Debe seleccionar un pedido");
            return;
        }

        // Verificar que tenga un repartidor asignado
        if(!pedido.getEstado().equals("Asignado")){
            JOptionPane.showMessageDialog(this,"Debe asignar un repartidor antes de iniciar la entrega.");
            return;
        }

        // Ejecutar el despcho del pedido
        pedido.despachar();

        JOptionPane.showMessageDialog(this,"Despacho realizado... Estado: " + pedido.getEstado());
        actualizarPedidos();
    }
}

