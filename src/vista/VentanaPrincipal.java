package vista;

import controlador.GestorPedidos;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

// Clase que representa la ventana principal del sistema

public class VentanaPrincipal extends JFrame {

    // Controlador para gestionar los pedidos
    private GestorPedidos gestorPedidos;

    // Componentes de la ventana
    private JLabel lblTitulo;
    private JButton btnRegistrar;
    private JButton btnRegistrarRepartidor;
    private JButton btnListar;
    private JButton btnAsignar;

    // Constructor
    public VentanaPrincipal(){

        gestorPedidos = new GestorPedidos();

        // Configuración de la ventana
        setTitle("SpeedFast - Gestión de entregas");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Título
        lblTitulo = new JLabel("Gestión de entregas");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Botones
        btnRegistrar = new JButton("Registrar pedido");
        btnRegistrarRepartidor = new JButton("Registrar repartidor");
        btnListar = new JButton("Listar pedidos");
        btnAsignar = new JButton("Asignar repartidor / Iniciar entrega");

        // Evento para abrir la ventana de registro de pedidos
        btnRegistrar.addActionListener(e -> {

            VentanaRegistroPedido ventanaRegistro =
                    new VentanaRegistroPedido(gestorPedidos);

            ventanaRegistro.setVisible(true);
        });

        // Evento para abrir la ventana de registro de repartidores
        btnRegistrarRepartidor.addActionListener(e -> {

            VentanaRegistroRepartidor ventanaRepartidor =
                    new VentanaRegistroRepartidor();

            ventanaRepartidor.setVisible(true);
        });

        // Evento para abrir la ventana del listado de pedidos
        btnListar.addActionListener(e -> {

            VentanaListaPedidos ventanaLista =
                    new VentanaListaPedidos(gestorPedidos);

            ventanaLista.setVisible(true);
        });

        // Evento para abrir la ventana de asignación de repartidores
        btnAsignar.addActionListener(e -> {

            VentanaAsignarRepartidor ventanaAsignar =
                    new VentanaAsignarRepartidor(gestorPedidos);

            ventanaAsignar.setVisible(true);
        });

        // Organización de los botones
        JPanel panelBotones = new JPanel();

        panelBotones.setLayout(
                new GridLayout(4, 1, 10, 10)
        );

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnRegistrarRepartidor);
        panelBotones.add(btnListar);
        panelBotones.add(btnAsignar);

        // Agregar componentes a la ventana
        add(lblTitulo, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
    }
}