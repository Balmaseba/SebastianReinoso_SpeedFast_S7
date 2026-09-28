package vista;

import dao.PedidoDAO;
import controlador.GestorPedidos;
import modelo.Pedido;
import modelo.PedidoComida;
import modelo.PedidoEncomienda;
import modelo.PedidoExpress;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

// Clase que representa la ventana de registro de pedidos


public class VentanaRegistroPedido  extends JFrame{

    // Controlador para gestionar los pedidos
    private GestorPedidos gestorPedidos;

    // Componentes del formulario
    private JTextField txtDireccion;
    private JTextField txtDistancia;
    private JTextField txtPeso;

    private JComboBox<String> cmbTipo;

    private JButton btnGuardar;

    private JPanel panelFormulario;
    private JPanel panelAdicional;

    // Constructor
    public VentanaRegistroPedido(GestorPedidos gestorPedidos) {
        this.gestorPedidos = gestorPedidos;

        // Configuración de la ventana
        setTitle("SpeedFast - Registrar pedido");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Crear componentes
        txtDireccion = new JTextField();
        txtDistancia = new JTextField();
        txtPeso = new JTextField(10);

        cmbTipo = new JComboBox<>(new String[]{
                "Comida", "Encomienda", "Express"
        });

        btnGuardar = new JButton("Guardar pedido");

        // Panel principal del formulario
        panelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));


        panelFormulario.add(new JLabel("Dirección:"));
        panelFormulario.add(txtDireccion);

        panelFormulario.add(new JLabel("Distancia (km):"));
        panelFormulario.add(txtDistancia);

        panelFormulario.add(new JLabel("Tipo de pedido:"));
        panelFormulario.add(cmbTipo);

        // Panel para los campos adicionales
        panelAdicional = new JPanel(new GridLayout(0, 2, 10, 10));

        // Panel para organizar los formularios
        JPanel panelSuperior = new JPanel(new BorderLayout(10,10));

        panelSuperior.add(panelFormulario, BorderLayout.NORTH);
        panelSuperior.add(panelAdicional, BorderLayout.CENTER);

        // Agregar componentes a la ventana
        add(panelSuperior, BorderLayout.NORTH);
        add(btnGuardar, BorderLayout.SOUTH);

        // Mostrar campos según el tipo de pedido
        actualizarCampos();

        // Eventos de los componentes
        cmbTipo.addActionListener(e -> actualizarCampos());
        btnGuardar.addActionListener(e -> guardarPedido());
    }

        // Método para actualizar los campos según el tipo de pedido
        private void actualizarCampos() {

            panelAdicional.removeAll();

            String tipo = (String) cmbTipo.getSelectedItem();

            if (tipo.equals("Encomienda")) {
                panelAdicional.add(new JLabel("Peso (kg):"));
                panelAdicional.add(txtPeso);
            }
            panelAdicional.revalidate();
            panelAdicional.repaint();
        }
    // Método para guardar un nuevo pedido

    private void guardarPedido() {

        // Obtener los datos ingresados
        String direccion = txtDireccion.getText().trim();
        String distanciaTexto = txtDistancia.getText().trim();
        String tipo = (String) cmbTipo.getSelectedItem();

        // Validar la dirección
        if (direccion.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar una dirección."
            );
            return;
        }

        // Validar la distancia
        if (distanciaTexto.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar la distancia."
            );
            return;
        }

        double distancia;

        try {

            distancia = Double.parseDouble(distanciaTexto);

            if (distancia <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "La distancia debe ser mayor a 0."
                );
                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "La distancia debe ser un número válido."
            );
            return;
        }

        // Variable para almacenar el pedido que se creará
        Pedido pedido;

        // Crear el pedido según el tipo seleccionado
        if (tipo.equals("Comida")) {

            pedido = new PedidoComida(
                    0,
                    direccion,
                    distancia
            );

        } else if (tipo.equals("Encomienda")) {

            String pesoTexto = txtPeso.getText().trim();

            // Validar el peso
            if (pesoTexto.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar el peso de la encomienda."
                );
                return;
            }

            double peso;

            try {

                peso = Double.parseDouble(pesoTexto);

                if (peso <= 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "El peso debe ser mayor a 0."
                    );
                    return;
                }

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "El peso debe ser un número válido."
                );
                return;
            }

            pedido = new PedidoEncomienda(
                    0,
                    direccion,
                    distancia,
                    peso
            );

        } else {

            pedido = new PedidoExpress(
                    0,
                    direccion,
                    distancia
            );
        }

        // Crear DAO para gestionar el pedido en la base de datos
        PedidoDAO pedidoDAO = new PedidoDAO();

        // Guardar primero el pedido en la base de datos
        if (pedidoDAO.guardar(pedido)) {

            // Registrar el pedido en el controlador con el ID generado
            if (gestorPedidos.registrarPedido(pedido)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Pedido registrado correctamente.\n"
                                + "ID generado: " + pedido.getNumeroPedido()
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "El pedido fue guardado en la base de datos, "
                                + "pero no se pudo registrar en el controlador."
                );
            }

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo guardar el pedido en la base de datos."
            );
        }
    }

}
