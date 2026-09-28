package vista;

import dao.RepartidorDAO;
import modelo.Repartidor;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

// Clase que representa la ventana para registrar repartidores

public class VentanaRegistroRepartidor extends JFrame {

    // Componentes de la ventana
    private JTextField txtNombre;
    private JButton btnGuardar;

    // DAO para gestionar los repartidores
    private RepartidorDAO repartidorDAO;

    // Constructor
    public VentanaRegistroRepartidor() {

        repartidorDAO = new RepartidorDAO();

        // Configuración de la ventana
        setTitle("SpeedFast - Registrar Repartidor");
        setSize(450, 140);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Crear componentes
        txtNombre = new JTextField();
        btnGuardar = new JButton("Guardar repartidor");

        // Panel para ingresar los datos
        JPanel panelDatos = new JPanel(
                new GridLayout(1, 2, 10, 10)
        );

        panelDatos.add(new JLabel("Nombre:"));
        panelDatos.add(txtNombre);

        // Panel para el botón
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);

        // Agregar componentes a la ventana
        add(panelDatos, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.SOUTH);

        // Evento para guardar el repartidor
        btnGuardar.addActionListener(e -> guardarRepartidor());
    }

    // Método para guardar un repartidor
    private void guardarRepartidor() {

        // Obtener el nombre ingresado
        String nombre = txtNombre.getText().trim();

        // Validar que se haya ingresado un nombre
        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar el nombre del repartidor."
            );

            return;
        }

        // Crear el repartidor
        Repartidor repartidor = new Repartidor(nombre);

        // Guardar el repartidor en la base de datos
        if (repartidorDAO.guardar(repartidor)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor registrado correctamente."
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el repartidor."
            );
        }
    }
}