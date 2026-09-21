package vista;

import controlador.GestorPedidos;
import modelo.Pedido;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

// Clase que representa la ventana del listado de pedidos

public class VentanaListaPedidos extends JFrame {

    // Controlador para gestionar los pedidos
    private GestorPedidos gestorPedidos;

    // Componentes de la ventana
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar;
    private JLabel lblTitulo;

    // Constructor
    public VentanaListaPedidos(GestorPedidos gestorPedidos) {

        this.gestorPedidos = gestorPedidos;

        // Configuración de la ventana
        setTitle("SpeedFast - Listado de Pedidos");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Titulo
        lblTitulo = new JLabel("Listado de Pedidos");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Definir las columnas de la tabla
        String[] columnas = {"ID", "Dirección", "Tipo", "Estado"};

        // Crear el modelo de la tabla
        modeloTabla = new DefaultTableModel(columnas, 0){
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        // Crear la tabla
        tablaPedidos = new JTable(modeloTabla);

        // Crar la barra de desplazamiento
        JScrollPane scrollTabla = new JScrollPane(tablaPedidos);

        // Crear botón para actualizar
        btnActualizar = new JButton("Actualizar tabla");

        // Panel inferior
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnActualizar);

        // Agregar componentes a la ventana
        add(lblTitulo,BorderLayout.NORTH);
        add(scrollTabla,BorderLayout.CENTER);
        add(panelBotones,BorderLayout.SOUTH);

        // Cargar los pedidos registrados
        actualizarTabla();

        // Evento para actualizar la tabla
        btnActualizar.addActionListener(e -> actualizarTabla());
    }

    // Método para actualizar los datos de la tabla
    private void actualizarTabla(){

        // Limpiar los datos existentes
        modeloTabla.setRowCount(0);

        // Recorrer los pedidos registrados
        for (Pedido pedido : gestorPedidos.getPedidos()){

            // Determinar el estado del pedido
            String estado;

            if(pedido.isCancelado()){
                estado = "Cancelado";
            } else {
                estado = "Pendiente";
            }

            // Obtener los datos del pedido
            Object[] fila = {
                    pedido.getNumeroPedido(),
                    pedido.getDireccionEntrega(),
                    pedido.getTipoPedido(),
                    pedido.getEstado()};

            // Agregar el pedido a la tabla
            modeloTabla.addRow(fila);

        }
    }
}
